package droidmathgraphics2d.assistant;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

public class AssistantGraphClass {

    private AssistantMethodClass assistant = new AssistantMethodClass();
    private TextClass text = new TextClass();

    public void drawViewportRectangle(int program, float xIni, float yIni, float xFin, float yFin){
        float[] viewportRectangle = {
                xIni, yIni, 0,
                xFin, yIni, 0,
                xFin, yIni, 0,
                xFin, yFin, 0,
                xFin, yFin, 0,
                xIni, yFin, 0,
                xIni, yFin, 0,
                xIni, yIni, 0
        };
        assistant.createObjectGraphic(program, viewportRectangle, false, false);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, (viewportRectangle.length / 3));
    }

    public void drawAxis(int program, float XMIN, float XMAX, float YMIN, float YMAX,
                      float XCURRTRANSATGI, float YCURRTRANSATGI){
        float[] axisX = {XMIN - XCURRTRANSATGI, 0, 0,//LEFT
                XMAX - XCURRTRANSATGI, 0, 0};//RIGHT
        float[] axisY = {0, YMIN - YCURRTRANSATGI, 0,//BOTTOM
                0, YMAX - YCURRTRANSATGI, 0};//TOP
        assistant.createObjectGraphic(program, axisX, false, false);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, (axisX.length / AssistantMethodClass.COORDS_PER_VERTEX));
        assistant.createObjectGraphic(program, axisY, false, false);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, (axisY.length / AssistantMethodClass.COORDS_PER_VERTEX));
    }

    public void drawTicks(int program, int width, int height, float XMIN, float XMAX, float YMIN, float YMAX,
                       float XCURRTRANSATGI, float YCURRTRANSATGI, int openingSizeXAxis, int openingSizeYAxis){
        List<Float> ticks = new ArrayList<Float>();
        //X ticks
        float stepX = (XMAX - XMIN) / openingSizeXAxis;
        //var syt = Math.abs((YMAX - YMIN) * 0.05 / 2);
        float syt = assistant.yPixelToCoordinate(10, height, YMIN, YMAX);
        for(float i = 0; i <= XMAX - XCURRTRANSATGI; i += stepX){
            ticks.add(i); ticks.add(-syt); ticks.add(0f);
            ticks.add(i); ticks.add(syt); ticks.add(0f);
        }
        for(float i = -stepX; i >= XMIN - XCURRTRANSATGI; i -= stepX){
            ticks.add(i); ticks.add(-syt); ticks.add(0f);
            ticks.add(i); ticks.add(syt); ticks.add(0f);
        }
        //Y ticks
        float stepY = (YMAX - YMIN) / openingSizeYAxis;
        //var sxt = Math.abs( ((XMAX - XMIN) * 0.035) / 2);
        float sxt = assistant.xPixelToCoordinate(10, width, XMIN, XMAX);
        for(float i = 0; i <= YMAX - YCURRTRANSATGI; i += stepY){
            ticks.add(-sxt); ticks.add(i); ticks.add(0f);
            ticks.add(sxt); ticks.add(i); ticks.add(0f);
        }
        for(float i = -stepY; i >= (YMIN - YCURRTRANSATGI); i -= stepY){
            ticks.add(-sxt); ticks.add(i); ticks.add(0f);
            ticks.add(sxt); ticks.add(i); ticks.add(0f);
        }
        float[] ticksArray = assistant.convertListToArrayFloat(ticks);
        assistant.createObjectGraphic(program, ticksArray, false, false);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, (ticksArray.length / AssistantMethodClass.COORDS_PER_VERTEX));
    }

    public void drawRectangularGrid(int program, float XMIN, float XMAX, float YMIN, float YMAX,
                                 float XCURRTRANSATGI, float YCURRTRANSATGI, int openingSizeXAxis, int openingSizeYAxis){
        List<Float> gridRectangle = new ArrayList<Float>();
        //X lines
        float stepX = (XMAX - XMIN) / openingSizeXAxis;
        for(float i = 0; i <= XMAX - XCURRTRANSATGI; i += stepX){
            gridRectangle.add(i); gridRectangle.add(YMIN - YCURRTRANSATGI); gridRectangle.add(0f);
            gridRectangle.add(i); gridRectangle.add(YMAX - YCURRTRANSATGI); gridRectangle.add(0f);
        }
        for(float i = -stepX; i >= XMIN - XCURRTRANSATGI; i -= stepX){
            gridRectangle.add(i); gridRectangle.add(YMIN - YCURRTRANSATGI); gridRectangle.add(0f);
            gridRectangle.add(i); gridRectangle.add(YMAX - YCURRTRANSATGI); gridRectangle.add(0f);
        }
        //Y lines
        float stepY = (YMAX - YMIN) / openingSizeYAxis;
        for(float i = 0; i <= YMAX - YCURRTRANSATGI; i += stepY){
            gridRectangle.add(XMIN - XCURRTRANSATGI); gridRectangle.add(i); gridRectangle.add(0f);
            gridRectangle.add(XMAX - XCURRTRANSATGI); gridRectangle.add(i); gridRectangle.add(0f);
        }
        for(float i = -stepY; i >= YMIN - YCURRTRANSATGI; i -= stepY){
            gridRectangle.add(XMIN - XCURRTRANSATGI); gridRectangle.add(i); gridRectangle.add(0f);
            gridRectangle.add(XMAX - XCURRTRANSATGI); gridRectangle.add(i); gridRectangle.add(0f);
        }
        float[] gridRectangleArray = assistant.convertListToArrayFloat(gridRectangle);
        assistant.createObjectGraphic(program, gridRectangleArray, false, false);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, (gridRectangleArray.length / AssistantMethodClass.COORDS_PER_VERTEX));
    }

    public void drawPolarGrid(int program, int width, int height, float XMIN, float XMAX, float YMIN, float YMAX,
                              float XCURRTRANSATGI, float YCURRTRANSATGI){
        List<Float> gridPolar = new ArrayList<Float>();
        float maxPolar = assistant.getMax(new float[]{
                Math.abs(XMIN), Math.abs(XMAX),
                Math.abs(YMIN), Math.abs(YMAX)
        });
        int sizeAspect = (width > height) ? width : height;
        float aspectX = ((XMAX - XMIN) * ((sizeAspect / 2) / maxPolar)) / width;
        float aspectY = ((YMAX - YMIN) * ((sizeAspect / 2) / maxPolar)) / height;
        float stepAngle = 1;
        float openingSizePolar = 4;//FIX VALUE
        float stepPolar = maxPolar / openingSizePolar;
        float[] radios = {
                createRadio((XMAX - XCURRTRANSATGI) / aspectX, (YMIN - YCURRTRANSATGI) / aspectY),
                createRadio((XMAX - XCURRTRANSATGI) / aspectX, (YMAX - YCURRTRANSATGI) / aspectY),
                createRadio((XMIN - XCURRTRANSATGI) / aspectX, (YMIN - YCURRTRANSATGI) / aspectY),
                createRadio((XMIN - XCURRTRANSATGI) / aspectX, (YMAX - YCURRTRANSATGI) / aspectY)
        };
        float maxRadio = assistant.getMax(radios);
        //Draw circumferences
        for(float i = stepPolar; i <= maxRadio + stepPolar; i+= stepPolar){
            float r = i;
            for(float a = 0; a <= 360; a+= stepAngle){
                float t = assistant.toRadians(a);
                float x = r * (float) Math.cos(t) * aspectX;
                float y = r * (float) Math.sin(t) * aspectY;
                float t2 = assistant.toRadians(a + stepAngle);
                float x2 = r * (float) Math.cos(t2) * aspectX;
                float y2 = r * (float) Math.sin(t2) * aspectY;
                if(boxXY(x, y, stepPolar, XMIN, XMAX, YMIN, YMAX, XCURRTRANSATGI, YCURRTRANSATGI) &&
                        boxXY(x2, y2, stepPolar, XMIN, XMAX, YMIN, YMAX, XCURRTRANSATGI, YCURRTRANSATGI)){
                    gridPolar.add(x); gridPolar.add(y); gridPolar.add(0f);
                    gridPolar.add(x2); gridPolar.add(y2); gridPolar.add(0f);
                }
            }
        }
        //Draw Rays
        int[] aperture = {0, 30, 60, 120, 150};
        float x1 = XMIN - maxRadio - XCURRTRANSATGI;
        float x2 = XMAX + maxRadio - XCURRTRANSATGI;
        for(int i = 0; i < aperture.length; i++){
            float y1 = (float) Math.tan(assistant.toRadians(aperture[i])) * x1;
            float y2 = (float) Math.tan(assistant.toRadians(aperture[i])) * x2;
            gridPolar.add(x1 * aspectX); gridPolar.add(y1 * aspectY); gridPolar.add(0f);
            gridPolar.add(x2 * aspectX); gridPolar.add(y2 * aspectY); gridPolar.add(0f);
        }
        //Vertical ray : angle = 90
        gridPolar.add(0f); gridPolar.add(YMIN - YCURRTRANSATGI); gridPolar.add(0f);
        gridPolar.add(0f); gridPolar.add(YMAX - YCURRTRANSATGI); gridPolar.add(0f);
        float[] gridPolarArray = assistant.convertListToArrayFloat(gridPolar);
        assistant.createObjectGraphic(program, gridPolarArray, false, false);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, (gridPolarArray.length / AssistantMethodClass.COORDS_PER_VERTEX));
    }

    public void drawNumberTagAxis(int program, float[] backgroundColor, int width, int height,
                                  float XMIN, float XMAX, float YMIN, float YMAX,
                                  float XCURRTRANSATGI, float YCURRTRANSATGI,
                                  int openingSizeXAxis, int openingSizeYAxis,
                                  float[] scaleVector, boolean bgImage){
        //Enable blend for transparent background in image tag
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDepthMask(false);

        int[] textBackground = {(int)backgroundColor[0] * 255, (int)backgroundColor[1] * 255, (int)backgroundColor[2] * 255};
        int[] textColor = {255, 0, 0};
        //X tags
        float stepX = (XMAX - XMIN) / openingSizeXAxis;
        for(float i = 0; i <= XMAX - XCURRTRANSATGI; i += stepX){
            text.drawText(program, " " + assistant.roundNumber(i / scaleVector[0], 3), assistant.roundNumber(i, 3), 0,
                    0, width, height, 15, "monospace", textColor, textBackground, XMIN, XMAX, YMIN, YMAX, bgImage);
        }
        for(float i = -stepX; i >= XMIN - XCURRTRANSATGI; i -= stepX){
            text.drawText(program, " " + assistant.roundNumber(i / scaleVector[0], 3), assistant.roundNumber(i, 3), 0,
                    0, width, height, 15, "monospace", textColor, textBackground, XMIN, XMAX, YMIN, YMAX, bgImage);
        }
        //Y tags
        float stepY = (YMAX - YMIN) / openingSizeYAxis;
        for(float i = stepY; i <= YMAX - YCURRTRANSATGI; i += stepY){
            text.drawText(program, " " + assistant.roundNumber(i / scaleVector[1], 3), 0, assistant.roundNumber(i, 3),
                    0, width, height, 15, "monospace", textColor, textBackground, XMIN, XMAX, YMIN, YMAX, bgImage);
        }
        for(float i = -stepY; i >= YMIN - YCURRTRANSATGI; i -= stepY){
            text.drawText(program, " " + assistant.roundNumber(i / scaleVector[1], 3), 0, assistant.roundNumber(i, 3),
                    0, width, height, 15, "monospace", textColor, textBackground, XMIN, XMAX, YMIN, YMAX, bgImage);
        }
    }

    public void drawNumberTagPolar(int program, boolean setImageBackground, float[] backgroundColor,
                                   int width, int height, float XMIN, float XMAX, float YMIN, float YMAX,
                                   float XCURRTRANSATGI, float YCURRTRANSATGI){
        //IF BACKGROUND IS NOT IMAGE DONT COLOCATE NEXT VALUES
        if(setImageBackground == true){
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            GLES20.glDepthMask(false);
        }

        float maxPolar = assistant.getMax(new float[]{
                Math.abs(XMIN), Math.abs(XMAX),
                Math.abs(YMIN), Math.abs(YMAX)});
        int sizeAspect = (width > height) ? width : height;
        float aspectX = ((XMAX - XMIN) * ((sizeAspect / 2) / maxPolar)) / width;
        float aspectY = ((YMAX - YMIN) * ((sizeAspect / 2) / maxPolar)) / height;
        float stepAngle = 1;
        float openingSizePolar = 4;//FIX VALUE
        float stepPolar = maxPolar / openingSizePolar;
        float[] radios = {
        createRadio((XMAX - XCURRTRANSATGI) / aspectX, (YMIN - YCURRTRANSATGI) / aspectY),
                createRadio((XMAX - XCURRTRANSATGI) / aspectX, (YMAX - YCURRTRANSATGI) / aspectY),
                createRadio((XMIN - XCURRTRANSATGI) / aspectX, (YMIN - YCURRTRANSATGI) / aspectY),
                createRadio((XMIN - XCURRTRANSATGI) / aspectX, (YMAX - YCURRTRANSATGI) / aspectY)};
        float maxRadio = assistant.getMax(radios);
        int[] textBackground = {(int)backgroundColor[0] * 255, (int)backgroundColor[1] * 255, (int)backgroundColor[2] * 255};
        int[] textColor = {255, 0, 255};
        int[] aperture = {0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330};
        int[] configPos = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int count = 0;
        for(float i = stepPolar; i <= maxRadio + stepPolar; i+= stepPolar){
            float r = i;
            if(count % 2 == 0){
                for(int angle = 0; angle < aperture.length; angle++){
                    float x = r * (float) Math.cos(assistant.toRadians(aperture[angle])) * aspectX;
                    float y = r * (float) Math.sin(assistant.toRadians(aperture[angle])) * aspectY;
                    if(boxXY(x, y, stepPolar, XMIN, XMAX, YMIN, YMAX, XCURRTRANSATGI, YCURRTRANSATGI)){
                        text.drawText(program, " " + aperture[angle], x, y,
                                configPos[angle],width, height, 13, "monospace", textColor, textBackground,
                                XMIN, XMAX, YMIN, YMAX, setImageBackground);
                    }
                }
            }
            count++;
        }
    }

    public void drawTicksBox(int program, int boxPosition, int width, int height,
                             int openingSizeXAxis, int openingSizeYAxis){
        List<Float> ticks = new ArrayList<Float>();
        float yPositionTick = 0;
        float xPositionTick = 0;
        switch(boxPosition){
            case 6000://TAG_UP_RIGHT
                yPositionTick = assistant.MaxY();
                xPositionTick = assistant.MaxX();
                break;
            case 6001://TAG_UP_LEFT
                yPositionTick = assistant.MaxY();
                xPositionTick = assistant.MinX();
                break;
            case 6002://TAG_BOTTOM_RIGHT
                yPositionTick = assistant.MinY();
                xPositionTick = assistant.MaxX();
                break;
            case 6003://TAG_BOTTOM_LEFT
                yPositionTick = assistant.MinY();
                xPositionTick = assistant.MinX();
                break;
        }

        //X ticks
        float stepX = (assistant.MaxX() - assistant.MinX()) / openingSizeXAxis;
        float syt = ((assistant.MaxY() - assistant.MinY()) / (assistant.MaxX() - assistant.MinX())) * 0.1f;
        for(float i = assistant.MinX() + stepX; i < assistant.MaxX(); i += stepX){
            ticks.add(i); ticks.add(-syt + yPositionTick); ticks.add(0f);
            ticks.add(i); ticks.add(syt + yPositionTick); ticks.add(0f);
        }
        //Y ticks
        float stepY = (assistant.MaxY() - assistant.MinY()) / openingSizeYAxis;
        float sxt =  (height / width)  * 0.1f;
        for(float i = assistant.MinY() + stepY; i < assistant.MaxY(); i += stepY){
            ticks.add(-sxt + xPositionTick); ticks.add(i); ticks.add(0f);
            ticks.add(sxt + xPositionTick); ticks.add(i); ticks.add(0f);
        }
        float[] ticksArray = assistant.convertListToArrayFloat(ticks);
        assistant.createObjectGraphic(program, ticksArray, false, false);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, (ticksArray.length / AssistantMethodClass.COORDS_PER_VERTEX));
    }

    public void drawNumberTagAxisBox(int program, int boxPosition, float[] backgroundColor, int width, int height,
                                     float XMIN, float XMAX, float YMIN, float YMAX,
                                     float XCURRTRANSATGI, float YCURRTRANSATGI,
                                     int openingSizeXAxis, int openingSizeYAxis,
                                     float[] scaleVector){
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDepthMask(false);

        float yPositionTag = 0;
        float xPositionTag = 0;
        int yModeTag = 0;
        int xModeTag = 3;
        int xFactorPosition = 0;
        int yFactorPosition = 0;
        switch(boxPosition){
            case 6000://TAG_UP_RIGHT
                yPositionTag = assistant.MaxY();
                xPositionTag = assistant.MaxX();
                yFactorPosition = -1;
                xFactorPosition = -1;
                yModeTag = 5;
                break;
            case 6001://TAG_UP_LEFT
                yPositionTag = assistant.MaxY();
                xPositionTag = assistant.MinX();
                yFactorPosition = -1;
                xFactorPosition = 1;
                yModeTag = 3;
                break;
            case 6002://TAG_BOTTOM_RIGHT
                yPositionTag = assistant.MinY();
                xPositionTag = assistant.MaxX();
                yFactorPosition = 1;
                xFactorPosition = -1;
                yModeTag = 5;
                break;
            case 6003://TAG_BOTTOM_LEFT
                yPositionTag = assistant.MinY();
                xPositionTag = assistant.MinX();
                yFactorPosition = 1;
                xFactorPosition = 1;
                yModeTag = 3;
                break;
        }

        int[] textBackground = {(int)backgroundColor[0] * 255, (int)backgroundColor[1] * 255, (int)backgroundColor[2] * 255};
        int[] textColor = {255, 0, 255};
        float xcurrtransatgi = XCURRTRANSATGI;
        float ycurrtransatgi = YCURRTRANSATGI;
        int count = 0;
        float stepX = (assistant.MaxX() - assistant.MinX()) / (float)openingSizeXAxis;
        float stepXTag = (XMAX - XMIN) / (float) openingSizeXAxis;

        //X tags
        List<String> tagsX = new ArrayList<String>();
        for(float i = XMIN; i <= XMAX; i += stepXTag){
            float numberTag = assistant.roundNumber(((i * scaleVector[0]) - xcurrtransatgi), 3);
            tagsX.add("" + numberTag);
        }
        count = 1;
        float syt = ((assistant.MaxY() - assistant.MinY()) / (assistant.MaxX() - assistant.MinX())) * 0.1f;
        for(float i = assistant.MinX() + stepX; i <= assistant.MaxX(); i += stepX){
            String numberTag = tagsX.get(count);
            text.drawText(program, " " + numberTag, assistant.roundNumber(i, 3), (yFactorPosition * 1.5f * syt) + yPositionTag,
                    xModeTag, width, height, 13, "monospace", textColor, textBackground,
                    assistant.MinX(), assistant.MaxX(), assistant.MinY(), assistant.MaxY(),
                    false);
            count ++;
        }

        //Y tags
        List<String> tagsY = new ArrayList<String>();
        float stepY = (assistant.MaxY() - assistant.MinY()) / (float)openingSizeYAxis;
        float stepYTag = (YMAX - YMIN) / (float) openingSizeYAxis;
        float sxt =  (height / width)  * 0.1f;
        for(float i = YMIN; i <= YMAX; i += stepYTag){
            float numberTag = assistant.roundNumber(((i * scaleVector[1]) - ycurrtransatgi), 3);
            tagsY.add("" + numberTag);
        }
        count = 1;
        for(float i = assistant.MinY() + stepY; i <= assistant.MaxY() - stepY; i += stepY){
            String numberTag = tagsY.get(count);
            text.drawText(program, " " + numberTag, (xFactorPosition * 1.5f * sxt) + xPositionTag, assistant.roundNumber(i, 3),
                    yModeTag, width, height, 13, "monospace", textColor, textBackground,
                    assistant.MinX(), assistant.MaxX(), assistant.MinY(), assistant.MaxY(),
                    false);
            count ++;
        }
    }

    public void drawTags(int program, float[] backgroundColor, int width, int height,
                         float XMIN, float XMAX, float YMIN, float YMAX){
        String[] tags = {"hello", "world", "xd"};
        int[] sizeTags = {17, 28, 14};
        String[] fontTags = {"monospace", "Courier New", "Arial Black"};
        int[] textBackground = {(int)backgroundColor[0] * 255, (int)backgroundColor[1] * 255, (int)backgroundColor[2] * 255};
        int[] textColor = {255, 0, 255};
        float jumpY = 0;
        float marginX = assistant.xPixelToCoordinate(20, width, XMIN, XMAX);
        float marginY = assistant.yPixelToCoordinate(5, height, YMIN, YMAX);
        float lineTagWidth = assistant.xPixelToCoordinate(15, width, XMIN, XMAX);
        float baselineIdeographicAprox = assistant.yPixelToCoordinate(1.2f, height, YMIN, YMAX);
        for(int i = 0; i < tags.length; i++){
            String t = tags[i];
            int s = sizeTags[i];
            String f = fontTags[i];
            List<Float> textProperties = text.getTextProperties(t, s, f);
            float wct = assistant.xPixelToCoordinate(textProperties.get(0), width, XMIN, XMAX);
            float hct = assistant.yPixelToCoordinate(textProperties.get(1), height, YMIN, YMAX);
            text.drawText(program, t, XMIN + marginX, YMAX - marginY - jumpY,
                    0, width, height, s, f, textColor, textBackground,
                    XMIN, XMAX, YMIN, YMAX, false);
            jumpY += hct;
            //Lines tag
            float[] lineTag = {XMIN, YMAX - marginY - jumpY + (hct / 2) + baselineIdeographicAprox, 0.0f,
                    XMIN + lineTagWidth, YMAX - marginY - jumpY + (hct / 2) + baselineIdeographicAprox, 0.0f};
            assistant.setBackgroundType(program, 0);
            assistant.createObjectGraphic(program, lineTag, true, false);
            assistant.setColor(program, 0.0f, 1.0f, 0.0f);
            GLES20.glDrawArrays(GLES20.GL_LINES, 0, (lineTag.length / AssistantMethodClass.COORDS_PER_VERTEX));
        }
    }

    private boolean boxXY(float x, float y, float stepPolar, float XMIN, float XMAX, float YMIN, float YMAX,
                   float XCURRTRANSATGI, float YCURRTRANSATGI){
        float xmin = XMIN - stepPolar - XCURRTRANSATGI;
        float xmax = XMAX + stepPolar - XCURRTRANSATGI;
        float ymin = YMIN - stepPolar - YCURRTRANSATGI;
        float ymax = YMAX + stepPolar - YCURRTRANSATGI;
        if((xmin < x && x <= xmax) &&
                (ymin < y && y <= ymax)){
            return true;
        }
        return false;
    }

    private float createRadio(float x, float y){
        float r = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return r;
    }





}
