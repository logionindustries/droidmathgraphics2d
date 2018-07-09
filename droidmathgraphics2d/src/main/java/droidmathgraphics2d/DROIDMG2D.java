package droidmathgraphics2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;

import java.util.ArrayList;
import java.util.List;

import droidmathgraphics2d.assistant.AssistantMethodClass;
import droidmathgraphics2d.interfaces.GenerateImageInterface;

public class DROIDMG2D extends GLSurfaceView {

    //Graph arrays
    private List<Double> xV = new ArrayList<Double>();
    private List<Double> yV = new ArrayList<Double>();
    private List<Double> xVP = new ArrayList<Double>();
    private List<Double> yVP = new ArrayList<Double>();
    private List<Double> xVV = new ArrayList<Double>();
    private List<Double> yVV = new ArrayList<Double>();
    private List<float[]> listFirstVector = new ArrayList<float[]>();
    private List<float[]> listSecondVector = new ArrayList<float[]>();
    private List<float[]> listFirstPointVectorVF = new ArrayList<float[]>();
    private List<float[]> listSecondPointVectorVF = new ArrayList<float[]>();
    private List<float[]> listFirstValueVectorVF = new ArrayList<float[]>();
    private List<float[]> listSecondValueVectorVF = new ArrayList<float[]>();
    private List<Integer> listGraphStyle = new ArrayList<Integer>();
    private List<Integer> listGraphPointSize = new ArrayList<Integer>();
    private List<Character> listGraphCharacter = new ArrayList<Character>();
    private List<Integer> listGraphThickness = new ArrayList<Integer>();
    private List<float[]> listGraphColor = new ArrayList<float[]>();
    private List<float[]> listGraphAreaColor = new ArrayList<float[]>();
    private List<String> listGraphFontFamily = new ArrayList<String>();
    private List<Integer> listGraphFontSize = new ArrayList<Integer>();
    private List<float[]> listVectorialFieldHeadColor = new ArrayList<float[]>();
    private List<float[]> listVectorialFieldTailColor = new ArrayList<float[]>();
    private List<float[]> listVectorialFieldBodyColor = new ArrayList<float[]>();
    //TRASLATE MOUSE VARS
    private float xInitial = 0, xFinal = 0, yInitial = 0, yFinal = 0;
    private float xTrans = 0, yTrans = 0;
    private float xCurrTrans = 0, yCurrTrans = 0;
    private boolean isDragged = false;
    //TRASLATE MOUSE VARS : AXIS, TAGS, GRID and INFINITY
    private float XTRANSATGI = 0;
    private float XCURRTRANSATGI = 0;
    private float YTRANSATGI = 0;
    private float YCURRTRANSATGI = 0;
    //FOR MOUSE VIEWPORT
    private boolean VIEWPORT_VISUALIZATION = false;
    private float xInitialViewport = 0;
    private float xFinalViewport = 0;
    private float yInitialViewport = 0;
    private float yFinalViewport = 0;
    //SIZE WINDOW GRAPH
    private float XMAX = 1;
    private float XMIN = -1;
    private float YMAX = 1;
    private float YMIN = -1;
    private boolean VIEWPORT_MANUAL = false;
    private int MARGIN_VIEWPORT = 0;

    private int CURRENT_GRAPH_STYLE = 1000;
    private int CURRENT_GRAPH_POINT_SIZE = 1;
    private char CURRENT_GRAPH_CHARACTER = '*';
    private int CURRENT_GRAPH_THICKNESS = 1;
    private float[] CURRENT_GRAPH_COLOR = {0.0f, 0.0f, 0.0f};
    private float[] CURRENT_GRAPH_AREA_COLOR = {0.0f, 0.0f, 0.0f};
    private String CURRENT_GRAPH_FONT_FAMILY = "Helvetica";
    private int CURRENT_GRAPH_FONT_SIZE = 12;
    private float[] CURRENT_VECTORIAL_FIELD_HEAD_COLOR = {0.0f, 0.0f, 0.0f};
    private float[] CURRENT_VECTORIAL_FIELD_TAIL_COLOR = {0.0f, 0.0f, 0.0f};
    private float[] CURRENT_VECTORIAL_FIELD_BODY_COLOR = {0.0f, 0.0f, 0.0f};

    //Properties constants
    //GRAPH STYLE
    public static final int NORMAL_FUNCTION = 1000;
    public static final int AREA_DOWN_CURVE_FUNCTION = 1001;
    public static final int AREA_DOWN_CURVE_BORDER_FUNCTION = 1002;
    public static final int CHARACTER_FUNCTION = 1003;
    public static final int DISPERSION_FUNCTION = 1004;
    //PLANE STYLE
    public static final int CARTESIAN_PLANE = 2000;
    public static final int POLAR_PLANE = 2001;
    //TAGS PRESENTATION
    public static final int TAG_UP_RIGHT = 6000;
    public static final int TAG_UP_LEFT = 6001;
    public static final int TAG_BOTTOM_RIGHT = 6002;
    public static final int TAG_BOTTOM_LEFT = 6003;
    //AXIS STYLE
    public static final int NORMAL_AXIS = 4000;
    public static final int BOX_AXIS = 4001;
    public static final int NONE_AXIS = 4002;
    //Colors
    public static final int INDIAN_RED = 1;
    public static final int CLEAR_CORAL = 2;
    public static final int SALMON = 3;
    public static final int DARK_SALMON = 4;
    public static final int CLEAR_SALMON = 5;
    public static final int CRIMSON = 6;
    public static final int RED = 7;
    public static final int BRICK_RED = 8;
    public static final int DARK_RED = 9;
    public static final int PINK = 10;
    public static final int CLEAR_PINK = 11;
    public static final int WARM_PINK = 12;
    public static final int THICK_PINK = 13;
    public static final int MEDIUM_RED_VIOLET = 14;
    public static final int STICK_RED_VIOLET = 15;
    public static final int CORAL = 16;
    public static final int TOMATO = 17;
    public static final int RED_ORANGE = 18;
    public static final int DARK_ORANGE = 19;
    public static final int ORANGE = 20;
    public static final int GOLDEN = 21;
    public static final int YELLOW = 22;
    public static final int MOCCASIN = 23;
    public static final int KHAKI = 24;
    public static final int DARK_KHAKI = 25;
    public static final int VIOLET = 26;
    public static final int ORCHID = 27;
    public static final int MAGENTA = 28;
    public static final int DARK_VIOLET = 29;
    public static final int PURPLE = 30;
    public static final int INDIGO = 31;
    public static final int BOARD_BLUE = 32;
    public static final int DARK_BOARD_BLUE = 33;
    public static final int YELLOW_GREEN = 34;
    public static final int LIME = 35;
    public static final int STICK_GREEN = 36;
    public static final int SPRING_GREEN = 37;
    public static final int MARINE_GREEN = 38;
    public static final int FOREST_GREEN = 39;
    public static final int GREEN = 40;
    public static final int DARK_GREEN = 41;
    public static final int OLIVE = 42;
    public static final int CLEAR_MARINE_GREEN = 43;
    public static final int DARK_CYAN = 44;
    public static final int BLUISH_GREEN = 45;
    public static final int CYAN = 46;
    public static final int TURQUOISE = 47;
    public static final int CADET_BLUE = 48;
    public static final int STEEL_BLUE = 49;
    public static final int BLUE = 50;
    public static final int MARINE_BLUE = 51;
    public static final int MIDNIGHT_BLUE = 52;
    public static final int PINK_BROWN = 53;
    public static final int PERU = 54;
    public static final int CHOCOLATE = 55;
    public static final int BROWN = 56;
    public static final int MARRON = 57;
    public static final int CLEAR_GRAY = 58;
    public static final int SILVER = 59;
    public static final int DARK_GRAY = 60;
    public static final int GRAY = 61;
    public static final int BOARD_GRAY = 62;
    public static final int WHITE = 63;
    public static final int BLACK = 64;

    private AssistantMethodClass assistant = new AssistantMethodClass();
    private final GraphRendererClass graph;
    private Context context;
    private boolean automaticExecuteImmediate = false;


    public DROIDMG2D(Context context, boolean automaticExecuteImmediate){
        super(context);
        this.context = context;
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        graph = new GraphRendererClass(context);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(graph);
        if(automaticExecuteImmediate) {
            this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }
        else{
            this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
        this.requestRender();
        this.automaticExecuteImmediate = automaticExecuteImmediate;
    }

    public DROIDMG2D(Context context, GenerateImageInterface gi, boolean automaticExecuteImmediate){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        graph = new GraphRendererClass(context, gi);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(graph);
        if(automaticExecuteImmediate) {
            this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }
        else{
            this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
        this.requestRender();
        this.automaticExecuteImmediate = automaticExecuteImmediate;
    }

    //1.- GENERAL METHODS
    public Bitmap getImageGraphic(){
        return graph.getImageGraph();
    }

    //2.- METHODS FOR VISUALIZATION PRESENTATION
    public void setViewport(double xmin, double xmax, double ymin, double ymax){
        XMIN = Float.parseFloat("" + xmin);
        XMAX = Float.parseFloat("" + xmax);
        YMIN = Float.parseFloat("" + ymin);
        YMAX = Float.parseFloat("" + ymax);
        VIEWPORT_MANUAL = true;
        graph.setXYProperties(XMIN, XMAX, YMIN, YMAX, XCURRTRANSATGI, YCURRTRANSATGI);
    }

    public void setAutomaticViewportMargin(int margin){
        MARGIN_VIEWPORT = margin;
    }

    //3.- MOUSE EVENTS METHODS TO MOVE OR SCALE OBJECTS
    //4. – TRANSFORMS
    public void restartTransformation(){
        restartTransformation(false);
    }

    public void restartTransformation(boolean executeImmediate){
        xTrans = 0;
        yTrans = 0;
        xCurrTrans = 0;
        yCurrTrans = 0;
        XTRANSATGI = 0;
        YTRANSATGI = 0;
        XCURRTRANSATGI = 0;
        YCURRTRANSATGI = 0;
        graph.setTranslation(0, 0);
        graph.setScale(1, 1);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void translate(double xt, double yt){
        translate(xt, yt, false);
    }

    public void translate(double xt, double yt, boolean executeImmediate){
        //Convert using original viewport [2, 2] and custom viewport [(XMAX - XMIN), (YMAX - YMIN)]
        float xtv = (Float.parseFloat("" + xt) * 2) / (XMAX - XMIN);
        float ytv = (Float.parseFloat("" + yt) * 2) / (YMAX - YMIN);
        xTrans += xtv;
        yTrans += ytv;
        graph.setTranslation(xTrans, yTrans);
        if(executeImmediate){
            this.requestRender();
        }
    }

    //5.- DESIGN ENVIRONMENT GRAPH
    public void setBackgroundImage(String pathImage){
        graph.setBackgroundImage(pathImage);
        this.requestRender();
    }

    public void setBackgroundImage(Bitmap image){
        graph.setBackgroundImage(image);
        this.requestRender();
    }

    public void removeBackgroundImage(){
        graph.removeBackgroundImage();
        this.requestRender();
    }

    public void setBackgroundColor(int r, int g, int b){
        setBackgroundColor(r, g, b, false);
    }

    public void setBackgroundColor(int r, int g, int b, boolean executeImmediate){
        graph.setBackgroundColor(r / 255f, g / 255f, b / 255f);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setBackgroundColor(int colorCode){
        setBackgroundColor(colorCode, false);
    }

    public void setBackgroundColor(int colorCode, boolean executeImmediate){
        float[] color = assistant.color(colorCode);
        graph.setBackgroundColor(color[0], color[1], color[2]);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setAxisXColor(int r, int g, int b){
        setAxisXColor(r, g, b, false);
    }

    public void setAxisXColor(int r, int g, int b, boolean executeImmediate){
        graph.setAxisXColor(r / 255f, g / 255f, b / 255f);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setAxisXColor(int colorCode){
        setAxisXColor(colorCode, false);
    }

    public void setAxisXColor(int colorCode, boolean executeImmediate){
        float[] color = assistant.color(colorCode);
        graph.setAxisXColor(color[0], color[1], color[2]);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setAxisYColor(int r, int g, int b){
        setAxisYColor(r, g, b, false);
    }

    public void setAxisYColor(int r, int g, int b, boolean executeImmediate){
        graph.setAxisYColor(r / 255f, g / 255f, b / 255f);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setAxisYColor(int colorCode){
        setAxisYColor(colorCode, false);
    }

    public void setAxisYColor(int colorCode, boolean executeImmediate){
        float[] color = assistant.color(colorCode);
        graph.setAxisYColor(color[0], color[1], color[2]);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setRaysColor(int r, int g, int b){
        setRaysColor(r, g, b, false);
    }

    public void setRaysColor(int r, int g, int b, boolean executeImmediate){
        graph.setRayColor(r / 255f, g / 255f, b / 255f);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setRaysColor(int colorCode){
        setRaysColor(colorCode, false);
    }

    public void setRaysColor(int colorCode, boolean executeImmediate){
        float[] color = assistant.color(colorCode);
        graph.setRayColor(color[0], color[1], color[2]);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setCircumferencesColor(int r, int g, int b){
        setCircumferencesColor(r, g, b, false);
    }

    public void setCircumferencesColor(int r, int g, int b, boolean executeImmediate){
        graph.setCircumferenceColor(r / 255f, g / 255f, b / 255f);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setCircumferencesColor(int colorCode){
        setCircumferencesColor(colorCode, false);
    }

    public void setCircumferencesColor(int colorCode, boolean executeImmediate){
        float[] color = assistant.color(colorCode);
        graph.setCircumferenceColor(color[0], color[1], color[2]);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setGridColor(int r, int g, int b){
        setGridColor(r, g, b, false);
    }

    public void setGridColor(int r, int g, int b, boolean executeImmediate){
        graph.setGridColor(r / 255f, g / 255f, b / 255f);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setGridColor(int colorCode){
        setGridColor(colorCode, false);
    }

    public void setGridColor(int colorCode, boolean executeImmediate){
        float[] color = assistant.color(colorCode);
        graph.setGridColor(color[0], color[1], color[2]);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setAxisNumbersColor(int r, int g, int b){
        setAxisNumbersColor(r, g, b, false);
    }

    public void setAxisNumbersColor(int r, int g, int b, boolean executeImmediate){
        graph.setNumberTagColor(r / 255f, g / 255f, b / 255f);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setAxisNumbersColor(int colorCode){
        setAxisNumbersColor(colorCode, false);
    }

    public void setAxisNumbersColor(int colorCode, boolean executeImmediate){
        float[] color = assistant.color(colorCode);
        graph.setNumberTagColor(color[0], color[1], color[2]);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setAxisNumbersFontFamily(String fontFamily){
        setAxisNumbersFontFamily(fontFamily, false);
    }

    public void setAxisNumbersFontFamily(String fontFamily, boolean executeImmediate){
        graph.setFontNumberTag(fontFamily);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setNumbersFontSize(int fontSize){
        setNumbersFontSize(fontSize, false);
    }

    public void setNumbersFontSize(int fontSize, boolean executeImmediate){
        graph.setFontSizeNumberTag(fontSize);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setAxisType(int type){
        setAxisType(type, false);
    }

    public void setAxisType(int type, boolean executeImmediate){
        switch(type){
            case 4000:
                graph.setAxisType(4000);
                graph.setDrawPlane(true);
                break;
            case 4001:
                graph.setAxisType(4001);
                graph.setDrawPlane(true);
                break;
            case 4002:
                graph.setAxisType(4002);
                graph.setDrawPlane(false);
                break;
            default:
                graph.setAxisType(4000);
                graph.setDrawPlane(true);
                break;
        }
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setPlaneType(int type){
        setPlaneType(type);
    }

    public void setPlaneType(int type, boolean executeImmediate){
        switch(type){
            case 2000: graph.setPlaneType(2000);
                break;
            case 2001: graph.setPlaneType(2001);
                break;
            default: graph.setPlaneType(2000);
                break;
        }
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void showGrid(boolean show){
        showGrid(show, false);
    }

    public void showGrid(boolean show, boolean executeImmediate){
        graph.setDrawGrid(show);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void showAxisNumbers(boolean show){
        showAxisNumbers(show, false);
    }

    public void showAxisNumbers(boolean show, boolean executeImmediate){
        graph.setDrawNumberTag(show);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setApertureGrid(int opening){
        setApertureGrid(opening, false);
    }

    public void setApertureGrid(int opening, boolean executeImmediate){
        graph.setApertureGrid(opening);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setThicknessGrid(int thickness){
        setThicknessGrid(thickness, false);
    }

    public void setThicknessGrid(int thickness, boolean executeImmediate){
        graph.setThicknessGrid(thickness);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setThicknessAxis(int thickness){
        setThicknessAxis(thickness, false);
    }

    public void setThicknessAxis(int thickness, boolean executeImmediate){
        graph.setThicknessAxis(thickness);
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setAxisBoxNumberTagPresentation(int presentation){
        setAxisBoxNumberTagPresentation(presentation, false);
    }

    public void setAxisBoxNumberTagPresentation(int presentation, boolean executeImmediate){
        switch(presentation){
            case 6000: case 6001: case 6002: case 6003:
                graph.setAxisBoxPosition(presentation);
                break;
            default:
                graph.setAxisBoxPosition(6002);
                break;
        }
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void addTag(String tag, int r, int g, int b){

    }

    public void addTag(String tag, int colorCode){

    }

    public void showTags(boolean show){

    }

    public void showTags(boolean show, boolean executeImmediate){
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void setTagPresentation(int presentation){

    }

    public void setTagPresentation(int presentation, boolean executeImmediate){
        if(executeImmediate){
            this.requestRender();
        }
    }

    public void showGraphTagPosition(boolean drawTagPosition){

    }

    //6.- GRAPH PROPERTIES
    public void setGraphStyle(int style){

    }

    public void setGraphPointSize(int size){

    }

    public void setGraphCharacter(char c){

    }

    public void setGraphThickness(double th){

    }

    public void setGraphColor(int r, int g, int b){

    }

    public void setGraphColor(int color){

    }

    public void setGraphAreaColor(int r, int g, int b){

    }

    public void setGraphAreaColor(int color){

    }

    public void setGraphCharacterFont(int font){

    }

    //7.- PROCESS GRAPH METHODS
    public void setCoordinatePoint(double firstValue, double secondValue){
        xV.add(firstValue);
        yV.add(secondValue);
    }

    public void setCartesianFunction(){
        listFirstVector.add(assistant.convertListToArrayDouble(xV));
        listSecondVector.add(assistant.convertListToArrayDouble(yV));
        listGraphStyle.add(CURRENT_GRAPH_STYLE);
        listGraphPointSize.add(CURRENT_GRAPH_POINT_SIZE);
        listGraphCharacter.add(CURRENT_GRAPH_CHARACTER);
        listGraphThickness.add(CURRENT_GRAPH_THICKNESS);
        listGraphColor.add(CURRENT_GRAPH_COLOR);
        listGraphAreaColor.add(CURRENT_GRAPH_AREA_COLOR);
        listGraphFontFamily.add(CURRENT_GRAPH_FONT_FAMILY);
        listGraphFontSize.add(CURRENT_GRAPH_FONT_SIZE);
        xV = new ArrayList<Double>();
        yV = new ArrayList<Double>();
        CURRENT_GRAPH_STYLE = 1000;
        CURRENT_GRAPH_POINT_SIZE = 1;
        CURRENT_GRAPH_CHARACTER = '*';
        CURRENT_GRAPH_THICKNESS = 1;
        CURRENT_GRAPH_COLOR = new float[] {0.0f, 0.0f, 0.0f};
        CURRENT_GRAPH_AREA_COLOR = new float[] {0.0f, 0.0f, 0.0f};
        CURRENT_GRAPH_FONT_FAMILY = "Helvetica";
        CURRENT_GRAPH_FONT_SIZE = 12;
    }

    public void processCartesianGraphics(){
        graph.setGraphicType(0);
        graph.setFirstVectorValues(listFirstVector);
        graph.setSecondVectorValues(listSecondVector);
        graph.setListGraphStyle(listGraphStyle);
        graph.setListGraphPointSize(listGraphPointSize);
        graph.setListGraphCharacter(listGraphCharacter);
        graph.setListGraphThickness(listGraphThickness);
        graph.setListGraphColor(listGraphColor);
        graph.setListGraphAreaColor(listGraphAreaColor);
        graph.setlistGraphFontFamily(listGraphFontFamily);
        graph.setListGraphFontSize(listGraphFontSize);
        if(!VIEWPORT_MANUAL == true){//If VIEWPORT_MANUAL = true, don´t calculate automatic max and min
            float[] minMax = graph.calculateMinAndMax();
            XMIN = minMax[0] - MARGIN_VIEWPORT;
            XMAX = minMax[1] + MARGIN_VIEWPORT;
            YMIN = minMax[2] - MARGIN_VIEWPORT;
            YMAX = minMax[3] + MARGIN_VIEWPORT;
        }
        graph.setXYProperties(XMIN, XMAX, YMIN, YMAX, XCURRTRANSATGI, YCURRTRANSATGI);
        //set temporal RENDERMODE_CONTINUOUSLY
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        queueEvent(new Runnable() {
            public void run() {
                requestRender();
                if(automaticExecuteImmediate) {
                    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
                }
                else{
                    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
                }
            }
        });
    }

    //8.- PROCESS VECTORIAL FIELD METHODS
    public void setVector(double xPoint, double yPoint, double xValue, double yValue){
        xVP.add(xPoint);
        yVP.add(yPoint);
        xVV.add(xValue);
        yVV.add(yValue);
    }

    public void setVectorialFieldFunction(){
        listFirstPointVectorVF.add(assistant.convertListToArrayDouble(xVP));
        listSecondPointVectorVF.add(assistant.convertListToArrayDouble(yVP));
        listFirstValueVectorVF.add(assistant.convertListToArrayDouble(xVV));
        listSecondValueVectorVF.add(assistant.convertListToArrayDouble(yVV));
        listVectorialFieldHeadColor.add(CURRENT_VECTORIAL_FIELD_HEAD_COLOR);
        listVectorialFieldTailColor.add(CURRENT_VECTORIAL_FIELD_TAIL_COLOR);
        listVectorialFieldBodyColor.add(CURRENT_VECTORIAL_FIELD_BODY_COLOR);
        xVP = new ArrayList<Double>();
        yVP = new ArrayList<Double>();
        xVV = new ArrayList<Double>();
        yVV = new ArrayList<Double>();
        CURRENT_VECTORIAL_FIELD_HEAD_COLOR = new float[] {0.0f, 0.0f, 0.0f};
        CURRENT_VECTORIAL_FIELD_TAIL_COLOR = new float[] {0.0f, 0.0f, 0.0f};
        CURRENT_VECTORIAL_FIELD_BODY_COLOR = new float[] {0.0f, 0.0f, 0.0f};
    }

    public void processVectorialFieldGraphics(){
        graph.setGraphicType(1);
        graph.setListFirstPointVectorVF(listFirstPointVectorVF);
        graph.setListSecondPointVectorVF(listSecondPointVectorVF);
        graph.setListFirstValueVectorVF(listFirstValueVectorVF);
        graph.setListSecondValueVectorVF(listSecondValueVectorVF);
        graph.setListVectorialFieldHeadColor(listVectorialFieldHeadColor);
        graph.setListVectorialFieldTailColor(listVectorialFieldTailColor);
        graph.setListVectorialFieldBodyColor(listVectorialFieldBodyColor);
        if(!VIEWPORT_MANUAL == true){//If VIEWPORT_MANUAL = true, don´t calculate automatic max and min
            float[] minMax = graph.calculateMinAndMax();
            XMIN = minMax[0] - MARGIN_VIEWPORT;
            XMAX = minMax[1] + MARGIN_VIEWPORT;
            YMIN = minMax[2] - MARGIN_VIEWPORT;
            YMAX = minMax[3] + MARGIN_VIEWPORT;
        }
        graph.setXYProperties(XMIN, XMAX, YMIN, YMAX, XCURRTRANSATGI, YCURRTRANSATGI);
        //set temporal RENDERMODE_CONTINUOUSLY
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        queueEvent(new Runnable() {
            public void run() {
                requestRender();
                if(automaticExecuteImmediate) {
                    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
                }
                else{
                    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
                }
            }
        });
    }

    public void setVectorialFieldTailColor(int r, int g, int b){
        CURRENT_VECTORIAL_FIELD_TAIL_COLOR = new float[] {r / 255f, g / 255f, b / 255f};
    }

    public void setVectorialFieldTailColor(int colorCode){
        float[] color = assistant.color(colorCode);
        CURRENT_VECTORIAL_FIELD_TAIL_COLOR = new float[] {color[0], color[1], color[2]};
    }

    public void setVectorialFieldHeadColor(int r, int g, int b){
        CURRENT_VECTORIAL_FIELD_HEAD_COLOR = new float[] {r / 255f, g / 255f, b / 255f};
    }

    public void setVectorialFieldHeadColor(int colorCode){
        float[] color = assistant.color(colorCode);
        CURRENT_VECTORIAL_FIELD_HEAD_COLOR = new float[] {color[0], color[1], color[2]};
    }

    public void setVectorialFieldBodyColor(int r, int g, int b){
        CURRENT_VECTORIAL_FIELD_BODY_COLOR = new float[] {r / 255f, g / 255f, b / 255f};
    }

    public void setVectorialFieldBodyColor(int colorCode){
        float[] color = assistant.color(colorCode);
        CURRENT_VECTORIAL_FIELD_BODY_COLOR = new float[] {color[0], color[1], color[2]};
    }

}
