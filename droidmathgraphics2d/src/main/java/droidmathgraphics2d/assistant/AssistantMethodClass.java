package droidmathgraphics2d.assistant;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AssistantMethodClass {

    public static final int COORDS_PER_VERTEX = 3;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public int createShaderProgram(){
        StringBuilder vertexShaderCode = new StringBuilder();
        vertexShaderCode.append("float x = 0.0, y = 0.0, z = 0.0;");
        vertexShaderCode.append("float xS = 0.0, yS = 0.0, zS = 0.0;");
        vertexShaderCode.append("float xR = 0.0, yR = 0.0, zR = 0.0;");
        vertexShaderCode.append("float angle = 0.0;");
        vertexShaderCode.append("attribute vec4 coordinates;");
        vertexShaderCode.append("uniform float pointSize;");
        vertexShaderCode.append("uniform vec4 translation;");
        vertexShaderCode.append("uniform vec4 scale;");
        vertexShaderCode.append("uniform mat4 ortho;");
        vertexShaderCode.append("uniform vec4 rotation;");
        vertexShaderCode.append("attribute vec2 texture_coord;");
        vertexShaderCode.append("varying vec2 v_texture_coord;");
        vertexShaderCode.append("vec4 coordinatesProjection;");
        vertexShaderCode.append("void main() {");
        vertexShaderCode.append("angle = radians(rotation[2]);");
        vertexShaderCode.append("coordinatesProjection = coordinates * ortho;");
        vertexShaderCode.append("x = coordinatesProjection[0]; y = coordinatesProjection[1]; z = coordinatesProjection[2];");
        vertexShaderCode.append("xS = scale[0] * x; yS = scale[1] * y; zS = scale[2] * z;");
        vertexShaderCode.append("xR = (xS * cos(angle)) - (yS * sin(angle));");
        vertexShaderCode.append("yR = (xS * sin(angle)) + (yS * cos(angle));");
        vertexShaderCode.append("gl_Position[0] = xR + translation[0];");
        vertexShaderCode.append("gl_Position[1] = yR + translation[1];");
        vertexShaderCode.append("gl_Position[2] = zR + translation[2];");
        vertexShaderCode.append("gl_Position[3] = (scale[3] * coordinates[3]) + translation[3];");
        vertexShaderCode.append("gl_PointSize = pointSize;");
        vertexShaderCode.append("v_texture_coord = texture_coord;");
        vertexShaderCode.append("}");

        StringBuilder fragmentShaderCode = new StringBuilder();
        fragmentShaderCode.append("precision mediump float;");
        fragmentShaderCode.append("uniform float R;");
        fragmentShaderCode.append("uniform float G;");
        fragmentShaderCode.append("uniform float B;");
        fragmentShaderCode.append("varying vec2 v_texture_coord;");
        fragmentShaderCode.append("uniform sampler2D s_texture;");
        fragmentShaderCode.append("uniform int typeBackground;");
        fragmentShaderCode.append("uniform vec4 color;");
        fragmentShaderCode.append("void main() {");
        fragmentShaderCode.append("if(typeBackground == 0)");
        fragmentShaderCode.append("{gl_FragColor = vec4(R, G, B, 1);}");
        fragmentShaderCode.append("if(typeBackground == 1)");
        fragmentShaderCode.append("{gl_FragColor = texture2D(s_texture, v_texture_coord);}");
        fragmentShaderCode.append("}");

        int mProgram = 0;
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode.toString());
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode.toString());

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        return mProgram;
    }

    //Return [coornidates location, texture location]
    public int[] createObjectGraphic(int program, float[] vertex, boolean haveTexture, boolean textureMultiple){
        int positionCoordinatesLoc;
        int positionTextureLoc;
        FloatBuffer vertexBuffer;
        ByteBuffer bb = null;
        //COORDINATES
        bb = ByteBuffer.allocateDirect(vertex.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertex);
        vertexBuffer.position(0);
        GLES20.glUseProgram(program);
        positionCoordinatesLoc = GLES20.glGetAttribLocation(program, "coordinates");
        GLES20.glEnableVertexAttribArray(positionCoordinatesLoc);
        GLES20.glVertexAttribPointer(positionCoordinatesLoc, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        //TEXTURE COORDINATES
        float[] textureCoords = {};
        List<Float> tc = new ArrayList<Float>();
        if(haveTexture == true){
            textureCoords = new float[]{
                    1.0f, 0.0f,
                    1.0f, 1.0f,
                    0.0f, 0.0f,
                    0.0f, 1.0f
            };
            if(textureMultiple == true){
                int nv = vertex.length / COORDS_PER_VERTEX;
                for(int i = 0; i < (nv / 6); i++) {
                    tc.add(0.0f); tc.add(0.0f);
                    tc.add(0.0f); tc.add(1.0f);
                    tc.add(1.0f); tc.add(0.0f);
                    tc.add(1.0f); tc.add(0.0f);
                    tc.add(0.0f); tc.add(1.0f);
                    tc.add(1.0f); tc.add(1.0f);
                }
                textureCoords = convertListToArrayFloat(tc);
            }
        }
        else{
            int nv = vertex.length / COORDS_PER_VERTEX;
            for(int i = 0; i < nv ; i++){
                tc.add(0.0f); tc.add(0.0f);
            }
            textureCoords = convertListToArrayFloat(tc);
        }

        FloatBuffer textureBuffer;
        bb = ByteBuffer.allocateDirect(textureCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        textureBuffer = bb.asFloatBuffer();
        textureBuffer.put(textureCoords);
        textureBuffer.position(0);
        positionTextureLoc = GLES20.glGetAttribLocation(program, "texture_coord");
        GLES20.glEnableVertexAttribArray (positionTextureLoc);
        GLES20.glVertexAttribPointer(positionTextureLoc, 2, GLES20.GL_FLOAT,
                false, 0, textureBuffer);
        int samplerTextureLoc = GLES20.glGetUniformLocation (program, "s_texture");
        GLES20.glUniform1i(samplerTextureLoc, 0);
        return new int[] {positionCoordinatesLoc, positionTextureLoc};
    }

    public void loadImageAndCreateTexture(Bitmap image){
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, image, 0);
    }

    public void orthographic(int program, float left, float right, float bottom, float top,
                             float near, float far){
        float XOP = 2.0f / (right - (left));
        float YOP = 2.0f / (top - (bottom));
        float ZOP = -2.0f / (far - (near));
        float[] ortho = {XOP, 0, 0, -(right + left) / (right - left),
                0, YOP, 0, -(top + bottom) / (top - bottom),
                0, 0, ZOP, -(far + near) / (far - near),
                0, 0, 0, 1};
        int orthoLocation = GLES20.glGetUniformLocation(program, "ortho");
        GLES20.glUniformMatrix4fv(orthoLocation, 1, false, ortho, 0);
    }

    public void setBackgroundType(int program, int type){
        int typeBGLocation = GLES20.glGetUniformLocation(program, "typeBackground");
        GLES20.glUniform1i(typeBGLocation, type);
    }

    public void setColor(int program, float r, float g, float b){
        int rLocation = GLES20.glGetUniformLocation(program, "R");
        int gLocation = GLES20.glGetUniformLocation(program, "G");
        int bLocation = GLES20.glGetUniformLocation(program, "B");
        GLES20.glUniform1f(rLocation, r);
        GLES20.glUniform1f(gLocation, g);
        GLES20.glUniform1f(bLocation, b);
    }

    public void setPointSize(int program, float size){
        int pointSize = GLES20.glGetUniformLocation(program, "pointSize");
        GLES20.glUniform1f(pointSize, size);
    }

    public void setScale(int program, float Sx, float Sy, float Sz){
        float[] scale = {Sx, Sy, Sz, 1};
        int scaleLocation = GLES20.glGetUniformLocation(program, "scale");
        GLES20.glUniform4fv(scaleLocation, 1, scale, 0);
    }

    public void setTranslate(int program, float Tx, float Ty, float Tz){
        int translateLocation = GLES20.glGetUniformLocation(program, "translation");
        GLES20.glUniform4f(translateLocation, Tx, Ty, Tz, 0.0f);
    }

    public void setRotate(int program, float Rx, float Ry, float Rz){
        float[] rot = {Rx, Ry, Rz, 1};
        int rotateLocation = GLES20.glGetUniformLocation(program, "rotation");
        GLES20.glUniform4fv(rotateLocation, 1, rot, 0);
    }

    public void drawBackground(int program, Bitmap image){
        float[] positionsBackground = { //POSITION FOR IMAGE
                1.0f, 1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f,
                -1.0f, -1.0f, 0.0f
        };
        loadImageAndCreateTexture(image);
        GLES20.glDepthMask(false);
        setBackgroundType(program, 1);
        int[] mPositionHandle = createObjectGraphic(program, positionsBackground, true, false);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glDisableVertexAttribArray(mPositionHandle[0]);
        GLES20.glDisableVertexAttribArray(mPositionHandle[1]);
        GLES20.glDepthMask(true);
    }

    public void setViewProperties(float[] backgroundColor, int width, int height){
        GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glViewport(0, 0, width, height);
    }


    public void resetTransform(int program){
        setScale(program, 1, 1, 1);
        setRotate(program, 0, 0, 0);
        setTranslate(program, 0, 0, 0);
        orthographic(program, -1, 1, -1, 1, -1, 1);
    }

    public float[] color(int colorCode){
        float[] colorVector = new float[3];
        float r = 0, g = 0, b = 0;
        switch(colorCode){
            case 1:r = 205;
                g = 92;
                b = 92;
                break;
            case 2:r = 240;
                g = 128;
                b = 128;
                break;
            case 3:r = 250;
                g = 128;
                b = 114;
                break;
            case 4:r = 233;
                g = 150;
                b = 122;
                break;
            case 5:r = 255;
                g = 160;
                b = 122;
                break;
            case 6:r = 220;
                g = 20;
                b = 60;
                break;
            case 7:r = 255;
                g = 0;
                b = 0;
                break;
            case 8:r = 178;
                g = 34;
                b = 34;
                break;
            case 9:r = 139;
                g = 0;
                b = 0;
                break;
            case 10:r = 255;
                g = 192;
                b = 203;
                break;
            case 11:r = 255;
                g = 182;
                b = 193;
                break;
            case 12:r = 255;
                g = 105;
                b = 180;
                break;
            case 13:r = 255;
                g = 20;
                b = 147;
                break;
            case 14:r = 199;
                g = 21;
                b = 133;
                break;
            case 15:r = 219;
                g = 112;
                b = 147;
                break;
            case 16:r = 255;
                g = 127;
                b = 80;
                break;
            case 17:r = 255;
                g = 99;
                b = 71;
                break;
            case 18:r = 255;
                g = 69;
                b = 0;
                break;
            case 19:r = 255;
                g = 140;
                b = 0;
                break;
            case 20:r = 255;
                g = 165;
                b = 0;
                break;
            case 21:r = 255;
                g = 215;
                b = 0;
                break;
            case 22:r = 255;
                g = 255;
                b = 0;
                break;
            case 23:r = 255;
                g = 228;
                b = 181;
                break;
            case 24:r = 240;
                g = 230;
                b = 140;
                break;
            case 25:r = 189;
                g = 183;
                b = 107;
                break;
            case 26:r = 238;
                g = 130;
                b = 238;
                break;
            case 27:r = 218;
                g = 112;
                b = 214;
                break;
            case 28:r = 255;
                g = 0;
                b = 255;
                break;
            case 29:r = 148;
                g = 0;
                b = 211;
                break;
            case 30:r = 128;
                g = 0;
                b = 128;
                break;
            case 31:r = 75;
                g = 0;
                b = 130;
                break;
            case 32:r = 106;
                g = 90;
                b = 205;
                break;
            case 33:r = 72;
                g = 61;
                b = 139;
                break;
            case 34:r = 173;
                g = 255;
                b = 47;
                break;
            case 35:r = 0;
                g = 255;
                b = 0;
                break;
            case 36:r = 152;
                g = 251;
                b = 152;
                break;
            case 37:r = 0;
                g = 255;
                b = 127;
                break;
            case 38:r = 46;
                g = 139;
                b = 87;
                break;
            case 39:r = 34;
                g = 139;
                b = 34;
                break;
            case 40:r = 0;
                g = 128;
                b = 0;
                break;
            case 41:r = 0;
                g = 100;
                b = 0;
                break;
            case 42:r = 128;
                g = 128;
                b = 0;
                break;
            case 43:r = 32;
                g = 178;
                b = 170;
                break;
            case 44:r = 0;
                g = 139;
                b = 139;
                break;
            case 45:r = 0;
                g = 128;
                b = 128;
                break;
            case 46:r = 0;
                g = 255;
                b = 255;
                break;
            case 47:r = 64;
                g = 224;
                b = 208;
                break;
            case 48:r = 95;
                g = 158;
                b = 160;
                break;
            case 49:r = 70;
                g = 130;
                b = 180;
                break;
            case 50:r = 0;
                g = 0;
                b = 255;
                break;
            case 51:r = 0;
                g = 0;
                b = 128;
                break;
            case 52:r = 25;
                g = 25;
                b = 112;
                break;
            case 53:r = 188;
                g = 143;
                b = 143;
                break;
            case 54:r = 205;
                g = 133;
                b = 63;
                break;
            case 55:r = 210;
                g = 105;
                b = 30;
                break;
            case 56:r = 164;
                g = 42;
                b = 42;
                break;
            case 57:r = 128;
                g = 0;
                b = 0;
                break;
            case 58:r = 211;
                g = 211;
                b = 211;
                break;
            case 59:r = 192;
                g = 192;
                b = 192;
                break;
            case 60:r = 169;
                g = 169;
                b = 169;
                break;
            case 61:r = 128;
                g = 128;
                b = 128;
                break;
            case 62:r = 112;
                g = 128;
                b = 144;
                break;
            case 63:r = 255;
                g = 255;
                b = 255;
                break;
            case 64:r = 0;
                g = 0;
                b = 0;
                break;
            default:r = 0;
                g = 0;
                b = 0;
                break;
        }
        colorVector[0] = r / 255f;
        colorVector[1] = g / 255f;
        colorVector[2] = b / 255f;
        return colorVector;
    }

    public float[] convertStringVectorToArray(String string){
        if(string.equals("")){
            return null;
        }
        string = string.substring(0, string.length() - 1);//Remove last ,
        String[] vecStr = string.split(",");
        float[] vec = new float[vecStr.length];
        for(int i = 0; i < vecStr.length; i++){
            vec[i] = Float.parseFloat(vecStr[i]);
        }
        return vec;
    }

    public float getMin(float[] vector){
        if(vector == null) return -1;
        float min = vector[0];
        for(int i = 0; i < vector.length; i++){
            if(vector[i] < min){
                min = vector[i];
            }
        }
        return min;
    }

    public float getMax(float[] vector){
        if(vector == null) return 1;
        float max = vector[0];
        for(int i = 0; i < vector.length; i++){
            if(vector[i] > max){
                max = vector[i];
            }
        }
        return max;
    }

    public float roundNumber(float num, int decimalToShow) {
        String decimal = "";
        for (int i = 0; i < decimalToShow; i++) {
            decimal += "#";
        }
        DecimalFormat df = new DecimalFormat("#." + decimal);
        df.setRoundingMode(RoundingMode.CEILING);
        float val = Float.parseFloat(df.format(num));
        return val;
    }

    //Converts from degrees to radians.
    public float toRadians(float degrees) {
        return degrees * (float) Math.PI / 180f;
    }

    // Converts from radians to degrees.
    public float toDegrees(float radians) {
        return radians * 180f / (float) Math.PI;
    }

    public boolean continuos(float number) {
        boolean c = false;
        if(Float.isNaN(number) == false && Float.isInfinite(number) == false) {
            c = true;
        } else {
            c = false;
        }
        return c;
    }

    //PHYSICAL COORDINATES [-1, 1, -1, 1]
    //LOGICAL COORDINATES [XMIN, XMAX, YMIN, YMAX]
    //Use for translate
    //Returns the logical x-coordinate of a physical x-coordinate:
    public float XL(float xc, int width) {
        return MinX() + ((xc / (float)width) * (MaxX() - MinX()));
    }

    // Returns the logical y-coordinate of a physical y-coordinate:
    public float YL(float yc, int height) {
        return MinY() - (((yc - height) / (float)height) * (MaxY() - MinY()));
    }

    //*Use for draw text
    //Convert pixel to x - coordinate logical
    public float xPixelToCoordinate (float xp, int width, float XMIN, float XMAX){
        return ((XMAX - XMIN) * xp) / (float)width;
    }

    //Convert pixel to y - coordinate logical
    public float yPixelToCoordinate(float yp, int height, float YMIN, float YMAX){
        return ((YMAX - YMIN) * yp) / (float)height;
    }

    //*Use for translate axis, tags, grid and infinity movement
    //Returns the logical x-coordinate of a physical x-coordinate (based in orthographic projection)
    public float XLATGI(float xp, int width, float XMIN, float XMAX) {
        return XMIN + ((xp / (float)width) * (XMAX - XMIN));
    }

    //Returns the logical y-coordinate of a physical y-coordinate (based in orthographic projection)
    public float YLATGI(float yp, int height, float YMIN, float YMAX) {
        return YMIN - (((yp - height) / (float) height) * (YMAX - YMIN));
    }

    private int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    //For normal logical coordinates
    public float MaxX() {
        return 1.0f ;
    }

    public float MinX() {
        return -1.0f ;
    }

    public float MaxY() {
        return 1.0f;
    }

    public float MinY() {
        return -1.0f;
    }

    public boolean hasGLES20(Activity activity) {
        ActivityManager am = (ActivityManager)
                activity.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x20000;
    }

    public float[] convertListToArrayFloat(List<Float> list){
        float[] array = new float[list.size()];
        for(int i = 0; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }

    public float[] convertListToArrayDouble(List<Double> list){
        float[] array = new float[list.size()];
        for(int i = 0; i < list.size(); i++){
            array[i] = Float.parseFloat("" + list.get(i));
        }
        return array;
    }

}
