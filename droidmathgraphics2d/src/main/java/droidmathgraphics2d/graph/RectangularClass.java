package droidmathgraphics2d.graph;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

import droidmathgraphics2d.assistant.AssistantMethodClass;
import droidmathgraphics2d.assistant.TextClass;

public class RectangularClass {

    private AssistantMethodClass assistant = new AssistantMethodClass();
    private TextClass textClass = new TextClass();
    public float xMax = 0;
    public float xMin = 0;
    public float yMax = 0;
    public float yMin = 0;

    public void rectangleGraphics(int program, List<float[]>xVectorList, List<float[]> yVectorList,
                                  int width, int height, float xmin, float xmax, float ymin, float ymax){
        for(int i = 0; i < xVectorList.size(); i++){
            float[] xVector = xVectorList.get(i);
            float[] yVector = yVectorList.get(i);
            float[] graph = createNormalFunction(xVector, yVector);
            int[] positionHandle = assistant.createObjectGraphic(program, graph, false, false);
            assistant.setColor(program, 1.0f, 0.0f, 0.0f);
            //3 elements per row (x, y, z)
            GLES20.glDrawArrays(GLES20.GL_LINES, 0, (graph.length / AssistantMethodClass.COORDS_PER_VERTEX));
            GLES20.glDisableVertexAttribArray(positionHandle[0]);
        }
    }

    public void findMinAndMax(List<float[]> xVectorList, List<float[]> yVectorList){
        String maxX = "", minX = "", maxY = "", minY = "";
        for(int i = 0; i < xVectorList.size(); i++){
            maxX += assistant.getMax(xVectorList.get(i)) + ",";
            minX += assistant.getMin(xVectorList.get(i)) + ",";
            maxY += assistant.getMax(yVectorList.get(i)) + ",";
            minY += assistant.getMin(yVectorList.get(i)) + ",";
        }
        this.xMax = assistant.getMax(assistant.convertStringVectorToArray(maxX));
        this.xMin = assistant.getMin(assistant.convertStringVectorToArray(minX));
        this.yMax = assistant.getMax(assistant.convertStringVectorToArray(maxY));
        this.yMin = assistant.getMin(assistant.convertStringVectorToArray(minY));

        this.xMax = Float.isNaN(this.xMax) ? 1 : this.xMax;
        this.xMin = Float.isNaN(this.xMin) ? -1 : this.xMin;
        this.yMax = Float.isNaN(this.yMax) ? 1 : this.yMax;
        this.yMin = Float.isNaN(this.yMin) ? -1 : this.yMin;
    }

    private float[] createNormalFunction(float[] firstVector, float[] secondVector){
        List<Float> vec = new ArrayList<Float>();
        for(int i = 0; i < firstVector.length - 1; i++){
            vec.add(firstVector[i]); vec.add(secondVector[i]); vec.add(0.0f);
            vec.add(firstVector[i + 1]); vec.add(secondVector[i + 1]); vec.add(0.0f);
        }
        float[] vecArray = assistant.convertListToArrayFloat(vec);
        return vecArray;
    }

    private float[] createDownCurveFunction(float[] firstVector, float[] secondVector){
        List<Float> vec = new ArrayList<Float>();
        for(int i = 0; i < firstVector.length - 1; i++){
            float x1 = firstVector[i];
            float x2 = firstVector[i + 1];
            float y1 = secondVector[i];
            float y2 = secondVector[i + 1];
            if(y1 >= 0 || y2 >= 0){
                vec.add(x2); vec.add(y2); vec.add(0.0f);
                vec.add(x1); vec.add(y1); vec.add(0.0f);
                vec.add(x2); vec.add(0.0f); vec.add(0.0f);
                vec.add(x2); vec.add(0.0f); vec.add(0.0f);
                vec.add(x1); vec.add(y1); vec.add(0.0f);
                vec.add(x1); vec.add(0.0f); vec.add(0.0f);
            }
            else if(y1 < 0 || y2 < 0){
                vec.add(x1); vec.add(0.0f); vec.add(0.0f);
                vec.add(x1); vec.add(y1); vec.add(0.0f);
                vec.add(x2); vec.add(0.0f); vec.add(0.0f);
                vec.add(x2); vec.add(0.0f); vec.add(0.0f);
                vec.add(x1); vec.add(y1); vec.add(0.0f);
                vec.add(x2); vec.add(y2); vec.add(0.0f);
            }
        }
        float[] vecArray = assistant.convertListToArrayFloat(vec);
        return vecArray;
    }
    
    private float[] createPointFunction(float[] firstVector, float[] secondVector){
        List<Float> vec = new ArrayList<Float>();
        for(int i = 0; i < firstVector.length; i++){
            vec.add(firstVector[i]); vec.add(secondVector[i]); vec.add(0.0f);
        }
        float[] vecArray = assistant.convertListToArrayFloat(vec);
        return vecArray;
    }

    public void createCharacterFunction(int program, String text, float[] firstVector, float[] secondVector,
                                        int fontSize, String fontFamily, int width, int height,
                                        float xmin, float xmax, float ymin, float ymax){
        int[] textColor = {255, 0, 0};
        int[] backgroundColor = {0, 0, 0};
        textClass.drawTextFunctionGraph(program, text, firstVector, secondVector, fontSize, fontFamily,
                width, height, xmin, xmax, ymin, ymax, textColor, backgroundColor);
    }

}
