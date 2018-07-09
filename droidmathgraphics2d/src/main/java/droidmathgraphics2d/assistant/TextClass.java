package droidmathgraphics2d.assistant;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.text.TextPaint;

import java.util.ArrayList;
import java.util.List;

public class TextClass {

    private AssistantMethodClass assistant = new AssistantMethodClass();

    public void drawText(int program, String text, float x, float y, int mode, int width, int height,
                         int fontSize, String fontFamily, int[] textColor, int[] backgroundColor,
                         float xmin, float xmax, float ymin, float ymax, boolean bgImage){
        List<Object> props = makeTextCanvas(text, fontSize, fontFamily, bgImage,
                textColor, backgroundColor);
        createTextuteText((Bitmap)props.get(0));
        drawTextFunction(program, x, y, (Float)props.get(1),
                (Float)props.get(2), mode,
                width, height, xmin, xmax, ymin, ymax);
    }

    public void drawTextFunctionGraph(int program, String text, float[] xv, float[] yv, int fontSize, String fontFamily,
                                      int width, int height, float xmin, float xmax, float ymin, float ymax,
                                      int[] textColor, int[] backgroundColor){
        List<Object> props = makeTextCanvas(text, fontSize, fontFamily, false, textColor, backgroundColor);
        createTextuteText((Bitmap)props.get(0));
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDepthMask(false);
        float MW = assistant.xPixelToCoordinate((Float)props.get(1), width, xmin, xmax) / 2f;
        float MH = assistant.yPixelToCoordinate((Float)props.get(2), height, ymin, ymax) / 2f;
        float CW = assistant.xPixelToCoordinate((Float)props.get(1), width, xmin, xmax);
        float CH = assistant.yPixelToCoordinate((Float)props.get(2), height, ymin, ymax);
        List<Float> positionsCharacters = new ArrayList<Float>();
        for(int i = 0; i < xv.length; i++){
            float x = xv[i];
            float y = yv[i];
            positionsCharacters.add(x - MW); positionsCharacters.add(y + MH); positionsCharacters.add(0.0f);
            positionsCharacters.add(x - MW); positionsCharacters.add(y - MH); positionsCharacters.add(0.0f);
            positionsCharacters.add(x + MW); positionsCharacters.add(y + MH); positionsCharacters.add(0.0f);
            positionsCharacters.add(x + MW); positionsCharacters.add(y + MH); positionsCharacters.add(0.0f);
            positionsCharacters.add(x - MW); positionsCharacters.add(y - MH); positionsCharacters.add(0.0f);
            positionsCharacters.add(x + MW); positionsCharacters.add(y - MH); positionsCharacters.add(0.0f);
        }
        float[] pc = assistant.convertListToArrayFloat(positionsCharacters);
        assistant.setBackgroundType(program, 1);
        assistant.createObjectGraphic(program, pc, true, true);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, pc.length / 3);
    }

    private void drawTextFunction(int program, float x, float y, float textWidth, float textHeight, int mode,
                                  int width, int height, float xmin, float xmax, float ymin, float ymax){
        int modeType = mode;
        float MW = assistant.xPixelToCoordinate(textWidth, width, xmin, xmax) / 2f;
        float MH = assistant.yPixelToCoordinate(textHeight, height, ymin, ymax) / 2f;
        float CW = assistant.xPixelToCoordinate(textWidth, width, xmin, xmax);
        float CH = assistant.yPixelToCoordinate(textHeight, height, ymin, ymax);
        float[] positionsBackground = {};
        switch(modeType){
            case 0://TOP - LEFT
                positionsBackground = new float[]{
                        x * (float) Math.cos(assistant.toRadians(180f)), y, 0.0f,
                        x * (float) Math.cos(assistant.toRadians(180f)), y - CH, 0.0f,
                        (x + CW) * (float) Math.cos(assistant.toRadians(180f)), y, 0.0f,
                        (x + CW) * (float) Math.cos(assistant.toRadians(180f)), y - CH, 0.0f
                };
                break;
            case 1://CENTER
                positionsBackground = new float[]{
                        (x - MW) * (float) Math.cos(assistant.toRadians(180f)), y + MH, 0.0f,
                        (x - MW) * (float) Math.cos(assistant.toRadians(180f)), y - MH, 0.0f,
                        (x + MW) * (float) Math.cos(assistant.toRadians(180f)), y + MH, 0.0f,
                        (x + MW) * (float) Math.cos(assistant.toRadians(180f)), y - MH, 0.0f
                };
                break;
            case 2://TOP - RIGHT
                positionsBackground = new float[]{
                        (x - CW) * (float) Math.cos(assistant.toRadians(180f)), y, 0.0f,
                        (x - CW) * (float) Math.cos(assistant.toRadians(180f)), y - CH, 0.0f,
                        x * (float) Math.cos(assistant.toRadians(180f)), y, 0.0f,
                        x * (float) Math.cos(assistant.toRadians(180f)), y - CH, 0.0f
                };
                break;
            case 3://BOTTOM - LEFT
                positionsBackground = new float[]{
                        x * (float) Math.cos(assistant.toRadians(180f)), y + CH, 0.0f,
                        x * (float) Math.cos(assistant.toRadians(180f)), y, 0.0f,
                        (x + CW) * (float) Math.cos(assistant.toRadians(180f)), y + CH, 0.0f,
                        (x + CW) * (float) Math.cos(assistant.toRadians(180f)), y, 0.0f
                };
                break;
            case 4://CENTER - LEFT
                positionsBackground = new float[]{
                        x * (float) Math.cos(assistant.toRadians(180f)), y + MH, 0.0f,
                        x * (float) Math.cos(assistant.toRadians(180f)), y - MH, 0.0f,
                        (x + CW) * (float) Math.cos(assistant.toRadians(180f)), y + MH, 0.0f,
                        (x + CW) * (float) Math.cos(assistant.toRadians(180f)), y - MH, 0.0f
                };
                break;
        }
        //*****BACKGROUND
        assistant.setBackgroundType(program, 1);
        assistant.createObjectGraphic(program, positionsBackground, true, false);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }

    private List<Object> makeTextCanvas(String text, int fontSize, String fontFamily, boolean bgImage,
                                        int[] textColor, int[] backgroundColor){
        List<Float> textProperties = getTextProperties(text, fontSize, fontFamily);
        float widthText = textProperties.get(0);
        float heightText = textProperties.get(1);
        Bitmap bitmap = Bitmap.createBitmap((int)widthText, (int)heightText, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);

        if(bgImage == true){
            //BACKGROUND TRANSPARENT
            bitmap.eraseColor(Color.TRANSPARENT);
        }
        else{
            //BACKGROUND COLOR
            canvas.drawColor(Color.rgb(backgroundColor[0], backgroundColor[1], backgroundColor[2]));
        }

        TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.rgb(textColor[0], textColor[1], textColor[2]));
        textPaint.setTextSize(fontSize);
        canvas.drawText(text, 0, fontSize, textPaint);

        List<Object> textProps = new ArrayList<Object>();
        textProps.add(bitmap);
        textProps.add((float)textPaint.measureText(text));
        textProps.add((float)fontSize);
        return textProps;
    }

    private void createTextuteText(Bitmap imageText){
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
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, imageText, 0);
        imageText.recycle();
    }

    public List<Float> getTextProperties(String text, int fontSize, String fontFamily){
        Canvas canvas = new Canvas();
        TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
        textPaint.setTextSize(fontSize);
        canvas.drawText(text, 0, fontSize, textPaint);
        List<Float> properties = new ArrayList<Float>();
        properties.add((float)textPaint.measureText(text));//width text
        properties.add(fontSize + textPaint.descent());//height text
        return properties;
    }

}
