package droidmathgraphics2d;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

import droidmathgraphics2d.assistant.AssistantMethodClass;
import droidmathgraphics2d.assistant.TextClass;
import droidmathgraphics2d.graph.DirectionalFieldsClass;
import droidmathgraphics2d.graph.RectangularClass;

public class GraphicsImplementationClass {

    private AssistantMethodClass assistant = new AssistantMethodClass();
    private TextClass text = new TextClass();
    //Program
    private int PROGRAM = 0;
    private RectangularClass rectangle = new RectangularClass();
    private DirectionalFieldsClass directionalField = new DirectionalFieldsClass();

    private float[] backgroundColor = {1, 1, 1};
    private float[] axisXColor = {0, 0, 0};
    private float[] axisYColor = {0, 0, 0};
    private float[] rayColor = {0, 0, 0};
    private float[] circumferenceColor = {0, 0, 0};
    private float[] gridColor = {0, 0, 0};
    private float[] numberTagColor = {0, 0, 0};
    private boolean setImageBackground = false;
    private Bitmap imageBackground;
    private float[] scaleVector = {1, 1};//[XS, YS]
    private float[] translateVector = {0, 0};//[XT, YT]
    private List<float[]> firstVectorValues = new ArrayList<float[]>();
    private List<float[]> secondVectorValues = new ArrayList<float[]>();
    //TRASLATE MOUSE VARS : AXIS, TAGS, GRID and INFINITY
    private float XCURRTRANSATGI = 0;
    private float YCURRTRANSATGI = 0;
    //SIZE WINDOW GRAPH
    private float XMAX = 0;
    private float XMIN = 0;
    private float YMAX = 0;
    private float YMIN = 0;

    private int openingSizeXAxis = 5;
    private int openingSizeYAxis = 5;
    private int BOX_POSITION = 6002;
    //Viewport
    private boolean VIEWPORT_VISUALIZATION = false;
    private float xInitialViewport = 0;
    private float xFinalViewport = 0;
    private float yInitialViewport = 0;
    private float yFinalViewport = 0;
    private String fontNumberTag = "monospace";
    private int fontSizeNumberTag = 13;
    private int axisType = 4000;
    private int planeType = 2000;
    private boolean drawPlane = false;
    private boolean drawGrid = false;
    private boolean drawNumberTag = false;
    private int apertureGrid = 5;
    private int thicknessGrid = 1;
    private int thicknessAxis = 1;
    private List<Integer> listGraphStyle = new ArrayList<Integer>();
    private List<Integer> listGraphPointSize = new ArrayList<Integer>();
    private List<Character> listGraphCharacter = new ArrayList<Character>();
    private List<Integer> listGraphThickness = new ArrayList<Integer>();
    private List<float[]> listGraphColor = new ArrayList<float[]>();
    private List<float[]> listGraphAreaColor = new ArrayList<float[]>();
    private List<String> listGraphFontFamily = new ArrayList<String>();
    private List<Integer> listGraphFontSize = new ArrayList<Integer>();

    //Vectorial field arrays
    private List<float[]> listFirstPointVectorVF = new ArrayList<float[]>();
    private List<float[]> listSecondPointVectorVF = new ArrayList<float[]>();
    private List<float[]> listFirstValueVectorVF = new ArrayList<float[]>();
    private List<float[]> listSecondValueVectorVF = new ArrayList<float[]>();
    private List<float[]> listVectorialFieldHeadColor = new ArrayList<float[]>();
    private List<float[]> listVectorialFieldTailColor = new ArrayList<float[]>();
    private List<float[]> listVectorialFieldBodyColor = new ArrayList<float[]>();
    //0 = CARTESIAN, 1 = VECTORIAL_FIELD, 2 = ALL[CARTESIAN AND VECTORIAL FIELD]
    private int GRAPHIC_TYPE = 0;
    private int width = 0;
    private int height = 0;

    public GraphicsImplementationClass(){
        PROGRAM = assistant.createShaderProgram();
    }

    public void init(int width, int height){
        this.width = width;
        this.height = height;
        setViewProperties();
    }

    public void display(int width, int height){
        this.width = width;
        this.height = height;
        setViewProperties();
        //draw background image
        resetTransform();
        drawBackground();

        //draw graphic functions and properties
        assistant.orthographic(PROGRAM, XMIN, XMAX, YMIN, YMAX, -1, 1);

        assistant.setScale(PROGRAM, scaleVector[0], scaleVector[1], 1);
        switch(GRAPHIC_TYPE){
            case 0:
                assistant.setBackgroundType(PROGRAM, 0);
                rectangle.rectangleGraphics(PROGRAM, firstVectorValues, secondVectorValues,
                        width, height, XMIN, XMAX, YMIN, YMAX);
                break;
            case 1:
                assistant.setBackgroundType(PROGRAM, 0);
                directionalField.vectorialFieldGraphics(PROGRAM, listFirstPointVectorVF, listSecondPointVectorVF,
                        listFirstValueVectorVF, listSecondValueVectorVF, width, height,
                        XMIN, XMAX, YMIN, YMAX);
                break;
            case 2:
                break;
        }
    }

    public void setGraphicType(int type){
        GRAPHIC_TYPE = type;
    }

    public float[] calculateMinAndMax(){
        float[] minMax = {};
        switch(GRAPHIC_TYPE){
            case 0:
                rectangle.findMinAndMax(firstVectorValues, secondVectorValues);
                minMax = new float[] {
                        rectangle.xMin,
                        rectangle.xMax,
                        rectangle.yMin,
                        rectangle.yMax
                };
                break;
            case 1:
                directionalField.findMinAndMax(listFirstPointVectorVF, listSecondPointVectorVF,
                        listFirstValueVectorVF, listSecondValueVectorVF);
                minMax = new float[] {
                        directionalField.xMin,
                        directionalField.xMax,
                        directionalField.yMin,
                        directionalField.yMax
                };
                break;
            case 2:
                break;
        }
        return minMax;
    }

    public void setViewportVisualization(boolean visualization){
        VIEWPORT_VISUALIZATION = visualization;
    }

    public void setViewportSize(float xI, float xF, float yI, float yF){
        xInitialViewport = xI;
        xFinalViewport = xF;
        yInitialViewport = yI;
        yFinalViewport = yF;
    }

    public void setBackgroundColor(float r, float g, float b){
        backgroundColor[0] = r;
        backgroundColor[1] = g;
        backgroundColor[2] = b;
    }

    public void setAxisXColor(float r, float g, float b){
        axisXColor[0] = r;
        axisXColor[1] = g;
        axisXColor[2] = b;
    }

    public void setAxisYColor(float r , float g, float b){
        axisYColor[0] = r;
        axisYColor[1] = g;
        axisYColor[2] = b;
    }

    public void setRayColor(float r, float g, float b){
        rayColor[0] = r;
        rayColor[1] = g;
        rayColor[2] = b;
    }

    public void setCircumferenceColor(float r, float g, float b){
        circumferenceColor[0] = r;
        circumferenceColor[1] = g;
        circumferenceColor[2] = b;
    }

    public void setGridColor(float r, float g, float b){
        gridColor[0] = r;
        gridColor[1] = g;
        gridColor[2] = b;
    }

    public void setNumberTagColor(float r, float g, float b){
        numberTagColor[0] = r;
        numberTagColor[1] = g;
        numberTagColor[2] = b;
    }

    public void setFontNumberTag(String font){
        fontNumberTag = font;
    }

    public void setFontSizeNumberTag(int size){
        fontSizeNumberTag = size;
    }

    public void setAxisType(int type){
        axisType = type;
    }

    public void setPlaneType(int type){
        planeType = type;
    }

    public void setDrawPlane(boolean draw){
        drawPlane = draw;
    }

    public void setAxisBoxPosition(int box_position){
        BOX_POSITION = box_position;
    }

    public void setDrawGrid(boolean draw){
        drawGrid = draw;
    }

    public void setDrawNumberTag(boolean draw){
        drawNumberTag = draw;
    }

    public void setApertureGrid(int aperture){
        apertureGrid = aperture;
    }

    public void setThicknessGrid (int thickness){
        thicknessGrid = thickness;
    }

    public void setThicknessAxis(int thickness){
        thicknessAxis = thickness;
    }

    public void setBackgroundImage(Bitmap imageBackground){
        this.imageBackground = imageBackground;
        this.setImageBackground = true;
    }

    public void removeBackgroundImage(){
        this.setImageBackground = false;
    }

    public void setFirstVectorValues(List<float[]> vectorValues){
        firstVectorValues = vectorValues;
    }

    public void setSecondVectorValues(List<float[]> vectorValues){
        secondVectorValues = vectorValues;
    }

    public void setListGraphStyle(List<Integer> list){
        listGraphStyle = list;
    }

    public void setListGraphPointSize(List<Integer> list){
        listGraphPointSize = list;
    }

    public void setListGraphCharacter(List<Character> list){
        listGraphCharacter = list;
    }

    public void setListGraphThickness(List<Integer> list){
        listGraphThickness = list;
    }

    public void setListGraphColor(List<float[]> list){
        listGraphColor = list;
    }

    public void setListGraphAreaColor(List<float[]> list){
        listGraphAreaColor = list;
    }

    public void setlistGraphFontFamily(List<String> list){
        listGraphFontFamily = list;
    }

    public void setListGraphFontSize(List<Integer> list){
        listGraphFontSize = list;
    }

    public void setListFirstPointVectorVF(List<float[]> list){
        listFirstPointVectorVF = list;
    }

    public void setListSecondPointVectorVF(List<float[]> list){
        listSecondPointVectorVF = list;
    }

    public void setListFirstValueVectorVF(List<float[]> list){
        listFirstValueVectorVF = list;
    }

    public void setListSecondValueVectorVF(List<float[]> list){
        listSecondValueVectorVF = list;
    }

    public void setListVectorialFieldHeadColor(List<float[]> list){
        listVectorialFieldHeadColor = list;
    }

    public void setListVectorialFieldTailColor(List<float[]> list){
        listVectorialFieldTailColor = list;
    }

    public void setListVectorialFieldBodyColor(List<float[]> list){
        listVectorialFieldBodyColor = list;
    }

    public void setTranslation(float xt, float yt){
        translateVector[0] = xt;
        translateVector[1] = yt;
    }

    public void setScale(float xs, float ys){
        scaleVector[0] = xs;
        scaleVector[1] = ys;
    }

    public void setXYProperties(float xmin, float xmax, float ymin, float ymax, float xtrans, float ytrans){
        XMIN = xmin;
        XMAX = xmax;
        YMIN = ymin;
        YMAX = ymax;
        XCURRTRANSATGI = xtrans;
        YCURRTRANSATGI = ytrans;
    }

    private void drawBackground(){
        if(setImageBackground){
            float[] positionsBackground = { //POSITION FOR IMAGE
                    1.0f, 1.0f, 0.0f,
                    1.0f, -1.0f, 0.0f,
                    -1.0f, 1.0f, 0.0f,
                    -1.0f, -1.0f, 0.0f
            };
            assistant.loadImageAndCreateTexture(imageBackground);
            GLES20.glDepthMask(false);
            assistant.setBackgroundType(PROGRAM, 1);
            int[] positionHandle = assistant.createObjectGraphic(PROGRAM, positionsBackground, true, false);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
            GLES20.glDisableVertexAttribArray(positionHandle[0]);
            GLES20.glDisableVertexAttribArray(positionHandle[1]);
            GLES20.glDepthMask(true);
        }
    }

    private void setViewProperties(){
        GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glViewport(0, 0, width, height);
    }

    private void resetTransform(){
        assistant.setScale(PROGRAM, 1, 1, 1);
        assistant.setRotate(PROGRAM, 0, 0, 0);
        assistant.setTranslate(PROGRAM, 0, 0, 0);
        assistant.orthographic(PROGRAM, -1, 1, -1, 1, -1, 1);
    }

}
