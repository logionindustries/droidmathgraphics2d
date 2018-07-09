package droidmathgraphics2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import droidmathgraphics2d.interfaces.GenerateImageInterface;


public class GraphRendererClass implements GLSurfaceView.Renderer {

    private int width = 0;
    private int height = 0;
    private GraphicsImplementationClass graphicsImpl;
    private GenerateImageInterface gi;
    private Bitmap IMAGE_GRAPH = null;


    public GraphRendererClass(Context context, GenerateImageInterface gi){
        this.gi = gi;
    }

    public GraphRendererClass(Context context){
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        graphicsImpl = new GraphicsImplementationClass();
        graphicsImpl.init(this.width, this.height);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        this.width = width;
        this.height = height;
        graphicsImpl.init(this.width, this.height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        graphicsImpl.display(this.width, this.height);
        IMAGE_GRAPH = rotateImageX(getBitmap(this.width, this.height));
        if(this.gi != null) {
            this.gi.imageGraphics(IMAGE_GRAPH);
        }
    }

    public void setGraphicType(int type){
        graphicsImpl.setGraphicType(type);
    }

    public float[] calculateMinAndMax(){
        return graphicsImpl.calculateMinAndMax();
    }

    public void setViewportVisualization(boolean visualization){
        graphicsImpl.setViewportVisualization(visualization);
    }

    public void setViewportSize(float xI, float xF, float yI, float yF){
        graphicsImpl.setViewportSize(xI, xF, yI, yF);
    }

    public void setBackgroundColor(float r, float g, float b){
        graphicsImpl.setBackgroundColor(r, g, b);
    }

    public void setAxisXColor(float r, float g, float b){
        graphicsImpl.setAxisXColor(r, g, b);
    }

    public void setAxisYColor(float r , float g, float b){
        graphicsImpl.setAxisYColor(r, g, b);
    }

    public void setRayColor(float r, float g, float b){
        graphicsImpl.setRayColor(r, g, b);
    }

    public void setCircumferenceColor(float r, float g, float b){
        graphicsImpl.setCircumferenceColor(r, g, b);
    }

    public void setGridColor(float r, float g, float b){
        graphicsImpl.setGridColor(r, g, b);
    }

    public void setNumberTagColor(float r, float g, float b){
        graphicsImpl.setNumberTagColor(r, g, b);
    }

    public void setFontNumberTag(String font){
        graphicsImpl.setFontNumberTag(font);
    }

    public void setFontSizeNumberTag(int size){
        graphicsImpl.setFontSizeNumberTag(size);
    }

    public void setAxisType(int type){
        graphicsImpl.setAxisType(type);
    }

    public void setPlaneType(int type){
        graphicsImpl.setPlaneType(type);
    }

    public void setDrawPlane(boolean draw){
        graphicsImpl.setDrawPlane(draw);
    }

    public void setAxisBoxPosition(int box_position){
        graphicsImpl.setAxisBoxPosition(box_position);
    }

    public void setDrawGrid(boolean draw){
        graphicsImpl.setDrawGrid(draw);
    }

    public void setDrawNumberTag(boolean draw){
        graphicsImpl.setDrawNumberTag(draw);
    }

    public void setApertureGrid(int aperture){
        graphicsImpl.setApertureGrid(aperture);
    }

    public void setThicknessGrid (int thickness){
        graphicsImpl.setThicknessGrid(thickness);
    }

    public void setThicknessAxis(int thickness){
        graphicsImpl.setThicknessAxis(thickness);
    }

    public void setBackgroundImage(String path){
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File file = new File(sdCard.getAbsolutePath() + "/" + path);
            FileInputStream streamIn = new FileInputStream(file);
            Bitmap bmp = BitmapFactory.decodeStream(streamIn);
            streamIn.close();
            graphicsImpl.setBackgroundImage(bmp);
        }
        catch(Exception e){
            graphicsImpl.removeBackgroundImage();
        }
    }

    public void setBackgroundImage(Bitmap image){
        if(image != null){
            graphicsImpl.setBackgroundImage(image);
        }
        else{
            graphicsImpl.removeBackgroundImage();
        }
    }

    public void removeBackgroundImage(){
        graphicsImpl.removeBackgroundImage();
    }

    public void setFirstVectorValues(List<float[]> vectorValues){
        graphicsImpl.setFirstVectorValues(vectorValues);
    }

    public void setSecondVectorValues(List<float[]> vectorValues){
        graphicsImpl.setSecondVectorValues(vectorValues);
    }

    public void setListGraphStyle(List<Integer> list){
        graphicsImpl.setListGraphStyle(list);
    }

    public void setListGraphPointSize(List<Integer> list){
        graphicsImpl.setListGraphPointSize(list);
    }

    public void setListGraphCharacter(List<Character> list){
        graphicsImpl.setListGraphCharacter(list);
    }

    public void setListGraphThickness(List<Integer> list){
        graphicsImpl.setListGraphThickness(list);
    }

    public void setListGraphColor(List<float[]> list){
        graphicsImpl.setListGraphColor(list);
    }

    public void setListGraphAreaColor(List<float[]> list){
        graphicsImpl.setListGraphAreaColor(list);
    }

    public void setlistGraphFontFamily(List<String> list){
        graphicsImpl.setlistGraphFontFamily(list);
    }

    public void setListGraphFontSize(List<Integer> list){
        graphicsImpl.setListGraphFontSize(list);
    }

    public void setListFirstPointVectorVF(List<float[]> list){
        graphicsImpl.setListFirstPointVectorVF(list);
    }

    public void setListSecondPointVectorVF(List<float[]> list){
        graphicsImpl.setListSecondPointVectorVF(list);
    }

    public void setListFirstValueVectorVF(List<float[]> list){
        graphicsImpl.setListFirstValueVectorVF(list);
    }

    public void setListSecondValueVectorVF(List<float[]> list){
        graphicsImpl.setListSecondValueVectorVF(list);
    }

    public void setListVectorialFieldHeadColor(List<float[]> list){
        graphicsImpl.setListVectorialFieldHeadColor(list);
    }

    public void setListVectorialFieldTailColor(List<float[]> list){
        graphicsImpl.setListVectorialFieldTailColor(list);
    }

    public void setListVectorialFieldBodyColor(List<float[]> list){
        graphicsImpl.setListVectorialFieldBodyColor(list);
    }

    public void setTranslation(float xt, float yt){
        graphicsImpl.setTranslation(xt, yt);
    }

    public void setScale(float xs, float ys){
        graphicsImpl.setScale(xs, ys);
    }

    public void setXYProperties(float xmin, float xmax, float ymin, float ymax, float xtrans, float ytrans){
        graphicsImpl.setXYProperties(xmin, xmax, ymin, ymax, xtrans, ytrans);
    }

    /*public void setBackground(boolean background){
        this.background = background;
        if(background){
            int id = this.context.getResources().getIdentifier("drawable/tierra", null,
                    this.context.getPackageName());
            // Temporary create a bitmap
            Bitmap bmp = BitmapFactory.decodeResource(this.context.getResources(), id);
            graphicsImpl.setImageBackground(bmp);
        }
        else{
            graphicsImpl.removeImageBackground();
        }
    }*/

    public Bitmap getImageGraph(){
        return this.IMAGE_GRAPH;
    }

    public Bitmap getBitmap(int mWidth, int mHeight) {
        ByteBuffer buf = ByteBuffer.allocateDirect(mWidth * mHeight * 4);
        buf.rewind();
        buf.order(ByteOrder.nativeOrder());
        GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
        GLES20.glReadPixels(0, 0, mWidth, mHeight, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf);
        Bitmap bmp = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        bmp.copyPixelsFromBuffer(buf);
        return bmp;
    }

    public Bitmap rotateImage(Bitmap image){
        Matrix matrix = new Matrix();
        matrix.postRotate(-180, image.getWidth(), image.getHeight());
        //Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmapOrg, width, height, true);
        Bitmap rotatedImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(),
                matrix, true);
        return rotatedImage;
    }

    public Bitmap rotateImageX(Bitmap image){
        Matrix matrix = new Matrix();
        Camera camera = new Camera();
        camera.save();
        camera.rotateX(-180);
        camera.getMatrix(matrix);
        camera.restore();
        Bitmap rotatedImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(),
                matrix, true);
        return rotatedImage;
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}
