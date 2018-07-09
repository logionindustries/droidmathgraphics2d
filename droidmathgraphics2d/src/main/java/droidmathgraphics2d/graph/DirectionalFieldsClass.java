package droidmathgraphics2d.graph;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

import droidmathgraphics2d.assistant.AssistantMethodClass;

public class DirectionalFieldsClass {

    private AssistantMethodClass assistant = new AssistantMethodClass();
    public float xMax = 0;
    public float xMin = 0;
    public float yMax = 0;
    public float yMin = 0;

    public void vectorialFieldGraphics(int program, List<float[]> xVectorPointList, List<float[]> yVectorPointList,
                                       List<float[]> xVectorList, List<float[]> yVectorList,
                                       int width, int height, float xmin, float xmax,
                                       float ymin, float ymax){
        for(int i = 0; i < xVectorPointList.size(); i++){
            float[] xv = xVectorList.get(i);
            float[] yv = yVectorList.get(i);
            float[] xp = xVectorPointList.get(i);
            float[] yp = yVectorPointList.get(i);

            createVectorialFieldGraphTail(program, xp, yp);
            drawDirections(program, xp, yp, xv, yv, height, this.yMin, this.yMax);
            createVectorialFieldGraph(program, xp, yp, xv, yv);
        }
    }

    public void findMinAndMax(List<float[]> xVectorPointList, List<float[]> yVectorPointList,
                              List<float[]> xVectorList, List<float[]> yVectorList){
        String maxXP = "", minXP = "", maxYP = "", minYP = "";
        String maxX = "", minX = "", maxY = "", minY = "";
        for(int i = 0; i < xVectorPointList.size(); i++){
            maxXP += assistant.getMax(xVectorPointList.get(i)) + ",";
            minXP += assistant.getMin(xVectorPointList.get(i)) + ",";
            maxYP += assistant.getMax(yVectorPointList.get(i)) + ",";
            minYP += assistant.getMin(yVectorPointList.get(i)) + ",";
            maxX += assistant.getMax(xVectorList.get(i)) + ",";
            minX += assistant.getMin(xVectorList.get(i)) + ",";
            maxY += assistant.getMax(yVectorList.get(i)) + ",";
            minY += assistant.getMin(yVectorList.get(i)) + ",";
        }
        float XMAXP = assistant.getMax(assistant.convertStringVectorToArray(maxXP));
        float XMINP = assistant.getMin(assistant.convertStringVectorToArray(minXP));
        float YMAXP = assistant.getMax(assistant.convertStringVectorToArray(maxYP));
        float YMINP = assistant.getMin(assistant.convertStringVectorToArray(minYP));
        float XMAXV = assistant.getMax(assistant.convertStringVectorToArray(maxX));
        float XMINV = assistant.getMin(assistant.convertStringVectorToArray(minX));
        float YMAXV = assistant.getMax(assistant.convertStringVectorToArray(maxY));
        float YMINV = assistant.getMin(assistant.convertStringVectorToArray(minY));

        this.xMax = assistant.getMax(assistant.convertStringVectorToArray(XMAXP + ", " + XMAXV + "," + (XMAXP + XMAXV) + ","));
        this.xMin = assistant.getMin(assistant.convertStringVectorToArray(XMINP + ", " + XMINV + "," + (XMINP + XMINV) + ","));
        this.yMax = assistant.getMax(assistant.convertStringVectorToArray(YMAXP + ", " + YMAXV + "," + (YMAXP + YMAXV) + ","));
        this.yMin = assistant.getMin(assistant.convertStringVectorToArray(XMINP + ", " + XMINV + "," + (YMINP + YMINV) + ","));

        this.xMax = Float.isNaN(this.xMax) ? 1 : this.xMax;
        this.xMin = Float.isNaN(this.xMin) ? -1 : this.xMin;
        this.yMax = Float.isNaN(this.yMax) ? 1 : this.yMax;
        this.yMin = Float.isNaN(this.yMin) ? -1 : this.yMin;
    }

    public void createVectorialFieldGraph(int program, float[] xp, float[] yp, float[] xv, float[] yv){
        List<Float> g = new ArrayList<Float>();
        for(int i = 0; i < xp.length; i++){
            g.add(xp[i]); g.add(yp[i]); g.add(0.0f);
            g.add(xp[i] + xv[i]); g.add(yp[i] + yv[i]); g.add(0.0f);
        }
        float[] gArray = assistant.convertListToArrayFloat(g);
        int[] positionHandle = assistant.createObjectGraphic(program, gArray, false, false);
        assistant.setColor(program, 1.0f, 0.0f, 0.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, (gArray.length / AssistantMethodClass.COORDS_PER_VERTEX));
        GLES20.glDisableVertexAttribArray(positionHandle[0]);
    }

    public void createVectorialFieldGraphTail(int program, float[] xp, float[] yp){
        List<Float> g = new ArrayList<Float>();
        for(int i = 0; i < xp.length; i++){
            g.add(xp[i]); g.add(yp[i]); g.add(0.0f);
        }
        float[] gArray = assistant.convertListToArrayFloat(g);
        int[] positionHandle = assistant.createObjectGraphic(program, gArray, false, false);
        assistant.setColor(program, 1.0f, 0.0f, 0.0f);
        assistant.setPointSize(program, 4.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, (gArray.length / AssistantMethodClass.COORDS_PER_VERTEX));
        GLES20.glDisableVertexAttribArray(positionHandle[0]);
    }

    private void drawDirections(int program, float[] xp, float[] yp, float[] xv, float[]yv, int height,
                                   float ymin, float ymax){
        List<Float> g = new ArrayList<Float>();
        float h = assistant.yPixelToCoordinate(7, height, ymin, ymax);
        for(int i = 0; i < xp.length; i++){
            float x = xp[i];
            float y = yp[i];
            float xf = xp[i] + xv[i];
            float yf = yp[i] + yv[i];
            float difx = xf - x;
            float dify = yf - y;
            float angle = (float)Math.atan2(dify, difx);

            float[] rp = rotatePointABOrigin(xf, yf, xf, yf + h, angle);
            g.add(rp[0]); g.add(rp[1]); g.add(0.0f);
            rp = rotatePointABOrigin(xf, yf, xf, yf - h, angle);
            g.add(rp[0]); g.add(rp[1]); g.add(0.0f);
            rp = rotatePointABOrigin(xf, yf, xf + h, yf, angle);
            g.add(rp[0]); g.add(rp[1]); g.add(0.0f);
        }
        float[] gArray = assistant.convertListToArrayFloat(g);
        int[] positionHandle = assistant.createObjectGraphic(program, gArray, false, false);
        assistant.setColor(program, 1.0f, 0.0f, 0.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, (gArray.length / AssistantMethodClass.COORDS_PER_VERTEX));
        GLES20.glDisableVertexAttribArray(positionHandle[0]);
    }

    private float[] rotatePointABOrigin(float a, float b, float x, float y, float angle){
        List<Float> points = new ArrayList<Float>();
        float xr = (((x - a) * (float)Math.cos(angle)) - ((y - b) * (float) Math.sin(angle))) + a;
        float yr = (((x - a) * (float) Math.sin(angle)) + ((y - b) * (float) Math.cos(angle))) + b;
        points.add(xr);
        points.add(yr);
        float[] pointsArray = assistant.convertListToArrayFloat(points);
        return pointsArray;
    }

}
