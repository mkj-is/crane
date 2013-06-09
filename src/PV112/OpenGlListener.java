
package PV112;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;


public class OpenGlListener implements GLEventListener {
    
    private GLUT glut = new GLUT();
    private GLU glu = new GLU();
    
    // models
    private int craneBottom;
    private int craneCabin;
    private int craneConsole;
    private int craneHook;
    private int skybox;
    private int surface;
    
    // textures
    private Texture craneTexture;
    private Texture asphaltTexture;
    private Texture skyboxTexture;
    
    // status
    private float craneRotation = 0.0f; // in degrees
    private float hookDistance = -20.0f; // from -60 to 30
    private float hookHeight = -100.0f; // -100 to 10
    private boolean on = false;
    private int grabbedItems = 0;
    
    // boxes
    public static final int BOX_COUNT = 15;
    public static final float MAGNET_TOLERANCY = 10.0f;
    public Set<Box> boxes = new HashSet<Box>();
    
    // camera
    // glu.gluLookAt(-200, 150, 100, 0, 100, 0, 0, 1, 0);
    public Camera cam = Camera.FREE_CAM;
    public float[] freeCamPosition = {-200, 150, 100, 0, 0, 0}; // x, y, z, rx, ry, rz
    public float[] cabinCamPosition = {0, 0, 0}; // rx, ry, rz
    
    // change positions
    public void rotateCrane(float amount)
    {
        craneRotation += amount;
        if(craneRotation < 0.0f)
        {
            craneRotation = 360.0f + craneRotation;
        }
        if(craneRotation > 360.0f)
        {
            craneRotation = craneRotation % 360.0f;
        }
        
    }
    
    public void moveHook(float amount)
    {
        hookDistance += amount;
        if(hookDistance > 30)
        {
            hookDistance = 30;
        }
        if(hookDistance < -60)
        {
            hookDistance = -60;
        }
    }
    
    public void pullHook(float amount)
    {
        hookHeight += amount;
        if(hookHeight > 10)
        {
            hookHeight = 10;
        }
        if(hookHeight < -100)
        {
            hookHeight = -100;
        } 
    }
    
    public void magnet()
    {
        if(on == false)
        {
            Set<Box> indexes = new HashSet<Box>();
            for(Box box: boxes)
            {
                if(Math.abs(box.rotation - craneRotation) < MAGNET_TOLERANCY
                        && Math.abs(Math.abs(box.position) - Math.abs(-64.0 + hookDistance)) < MAGNET_TOLERANCY
                        && hookHeight < -90)
                {
                    indexes.add(box);
                }
            }
            boolean success = boxes.removeAll(indexes);
            grabbedItems = indexes.size();
            System.out.println("boxes added: " + grabbedItems + success);
        }
        else
        {
            for(int i = 0; i < grabbedItems; i++)
            {
                Box box = new Box((int)(craneRotation - 10.0 + Math.random() * 20.0), (int)(-64.0 + hookDistance - 10.0 + Math.random() * 20.0));
                boxes.add(box);
            }
            System.out.println("boxes released: " + grabbedItems);
            grabbedItems = 0;
        }
        on = !on;
    }
    
    public void changeCamera()
    {
        switch(cam)
        {
            case FREE_CAM:
                cam = Camera.CABIN_CAM;
                break;
            case CABIN_CAM:
                cam = Camera.HOOK_CAM;
                break;
            default:
                cam = Camera.FREE_CAM;
        }
    }
    
    public void moveFreeCamera(float x, float y, float z)
    {
        freeCamPosition[0] += x;
        freeCamPosition[1] += y;
        freeCamPosition[2] += z;
    }
    
    
    
    @Override
    // metoda volana pri vytvoreni okna OpenGL
    @SuppressWarnings("empty-statement")
    public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        
        //gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_SMOOTH);
        
        // models
        craneBottom = ObjLoader.loadWavefrontObjectAsDisplayList(gl, "/resources/objects/crane_bottom.obj");
        craneCabin = ObjLoader.loadWavefrontObjectAsDisplayList(gl, "/resources/objects/crane_cabin.obj");
        craneConsole = ObjLoader.loadWavefrontObjectAsDisplayList(gl, "/resources/objects/crane_console.obj");
        craneHook = ObjLoader.loadWavefrontObjectAsDisplayList(gl, "/resources/objects/crane_hook.obj");
        skybox = ObjLoader.loadWavefrontObjectAsDisplayList(gl, "/resources/objects/skybox.obj");
        surface = ObjLoader.loadWavefrontObjectAsDisplayList(gl, "/resources/objects/surface.obj");
        
        //textures
        URL textureUrl = getClass().getResource("/resources/textures/texture_flipped.jpg");
        try {
            TextureData data = TextureIO.newTextureData(glad.getGLProfile(), textureUrl, true, TextureIO.JPG);
            craneTexture = new Texture(gl, data);
        } catch (IOException e) {
            System.err.println("File not found");
        }
        URL skyboxUrl = getClass().getResource("/resources/textures/skybox_flipped.jpg");
        try {
            TextureData data = TextureIO.newTextureData(glad.getGLProfile(), skyboxUrl, true, TextureIO.JPG);
            skyboxTexture = new Texture(gl, data);
        } catch (IOException e) {
            System.err.println("File not found");
        }
        URL asphaltUrl = getClass().getResource("/resources/textures/asphalt.jpg");
        try {
            TextureData data = TextureIO.newTextureData(glad.getGLProfile(), asphaltUrl, true, TextureIO.JPG);
            asphaltTexture = new Texture(gl, data);
        } catch (IOException e) {
            System.err.println("File not found");
        }
        
        // texture settings
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_TEXTURE_ENV_MODE,GL2.GL_MODULATE);
        
        // init boxes
        for(int i = 0; i <= BOX_COUNT; i++){
            boxes.add(new Box());
        }
        
        // redraw scene periodically
        FPSAnimator animator = new FPSAnimator(glad, 30);
        animator.add(glad);
        animator.start();
    }
    

    @Override
    // metoda volana pri zatvoreni okna OpenGL
    public void dispose(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        
        gl.glDeleteLists(craneBottom, craneBottom);
        gl.glDeleteLists(craneCabin, craneCabin);
        gl.glDeleteLists(craneConsole, craneHook);
        gl.glDeleteLists(craneHook, craneHook);
        gl.glDeleteLists(skybox, skybox);
        gl.glDeleteLists(surface, surface);
        
        
    }
    

    @Override
    // metoda volana pri kazdom prekresleni obrazovky 
   public void display(GLAutoDrawable glad) { 
        GL2 gl = glad.getGL().getGL2();
        
        //System.out.println("displayed" + frame);
        //frame++;
        
        gl.glEnable(GL2.GL_CULL_FACE);
        
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        
        
        // skybox
        gl.glDisable(GL2.GL_DEPTH_TEST);
        gl.glDepthMask(false);
        skyboxTexture.bind(gl);
        gl.glCallList(skybox);
        gl.glDepthMask(true);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        
        // lighting
        doLighting(gl);
        
        // surface
        asphaltTexture.bind(gl);
        gl.glCallList(surface);
        
        // crane
        craneTexture.bind(gl);
        gl.glCallList(craneBottom);
        
        // boxes
        //gl.glLoadIdentity();
        gl.glPushMatrix();
        
        for(Box box: boxes)
        {
            //gl.glPopMatrix();
            //gl.glPushMatrix();
            
            //gl.glRotatef(craneRotation, 0, 1, 0);
            gl.glRotatef(box.rotation, 0, 1, 0);
            gl.glTranslatef(box.position, 2, 0);
            drawBox(gl);
            gl.glLoadIdentity();
            gl.glPopMatrix();
            gl.glPushMatrix();
            //System.out.println("draw box");
        }
        
        // attached box
        if(on && grabbedItems > 0)
        {
            gl.glRotatef(craneRotation, 0, 1, 0);
            gl.glTranslatef(-64 + hookDistance, 2 + 100 + hookHeight, 0);
            gl.glDisable(GL2.GL_TEXTURE_2D);
            glut.glutSolidCube(4);
            gl.glEnable(GL2.GL_TEXTURE_2D);
            gl.glTranslatef(64 - hookDistance, -2 - 100 - hookHeight, 0);
            gl.glRotatef(craneRotation, 0, -1, 0);
        }
        
        // rest of crane
        
        gl.glRotatef(craneRotation, 0, 1, 0);
        
        gl.glCallList(craneCabin);
        
        cabinSpotlight(gl);
        
        gl.glCallList(craneConsole);
        
        hookSpotlight(gl);
        
        // hook
        gl.glTranslatef(hookDistance, hookHeight, 0);
        
        // rope
        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glLineWidth(2); 
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(-64, 104.5f, 0);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(-64, 125.3f - hookHeight, 0);
        gl.glEnd();

        gl.glEnable(GL2.GL_TEXTURE_2D);
        
        gl.glCallList(craneHook);
        
        // free camera
        gl.glLoadIdentity();
        if(cam == Camera.FREE_CAM)
        {
            glu.gluLookAt(freeCamPosition[0], freeCamPosition[1], freeCamPosition[2], 0, 100, 0, 0, 1, 0);
        }
        if(cam == Camera.HOOK_CAM) {
            glu.gluLookAt(-64 + hookDistance + 1, 120, 0, -64 + hookDistance + 1, 0, 0, -1, 0, 0);
        }
        //gl.glLoadIdentity();
        
    }
    

    @Override
    // metoda volana pri zmene velkosti okna
    public void reshape(GLAutoDrawable glad, int x, int y, int width, int height) {
        GL2 gl = glad.getGL().getGL2();
        gl.glViewport(x, y, width, height);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, (float)width/height, 1, 5000);
    }
    
    private void doLighting( GL2 gl )
    {
        //float[] lightPos = {300, 200, 300, 1};
        float[] lightPos = {0, 250, 0, 1};
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        float[] noAmbient ={ 0.1f, 0.1f, 0.1f, 1f }; // low ambient light
        float[] spec = { 0.5f, 0.1f, 0f, 1f }; // low ambient light
        float[] diffuse ={ 0.5f, 0.5f, 0.5f, 1f };
        // properties of the light
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, noAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, spec, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        
    }
    
    private void drawBox(GL2 gl)
    {
        gl.glDisable(GL2.GL_TEXTURE_2D);
        glut.glutSolidCube(4);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        
    }
    
    private void hookSpotlight(GL2 gl)
    {
        float spot_ambient[] =  {0.2f,0.1f,0.1f,1.0f };
        float spot_diffuse[] =  {0.5f,0.1f,0.1f,1.0f };
        float spot_specular[] =  {1f,0.1f,0.1f,1.0f };
        
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT,  spot_ambient,0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE,  spot_diffuse,0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, spot_specular,0);
        gl.glEnable(GL2.GL_LIGHT1);

        float spot_position[] =  {-64.0f + hookDistance,125.0f,0.0f,1.0f};
        float spot_direction[] = {0.0f,-1.0f,0.0f};
        float spot_angle = 25.0f;
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION,  spot_position,0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION,spot_direction,0);
        gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF,(float)spot_angle);

        gl.glLighti(GL2.GL_LIGHT1, GL2.GL_SPOT_EXPONENT, 10);
    }
    
    private void cabinSpotlight(GL2 gl)
    {
        float spot_ambient[] =  {0.2f,0.1f,0.1f,1.0f };
        float spot_diffuse[] =  {0.1f,0.5f,0.1f,1.0f };
        float spot_specular[] =  {0.1f,1.0f,0.1f,1.0f };
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT,  spot_ambient,0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE,  spot_diffuse,0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, spot_specular,0);
        gl.glEnable(GL2.GL_LIGHT2);

        float spot_position[] =  {0,160.0f,0.0f,1.0f};
        float spot_direction[] = {0.0f,-1.0f,0.0f};
        float spot_angle = 25.0f;
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION,  spot_position,0);
        gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPOT_DIRECTION,spot_direction,0);
        gl.glLightf(GL2.GL_LIGHT2, GL2.GL_SPOT_CUTOFF,(float)spot_angle);

        gl.glLighti(GL2.GL_LIGHT2, GL2.GL_SPOT_EXPONENT, 10);
    }
}
