
package PV112;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.j3d.Transform3D;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3f;


public class OpenGlListener implements GLEventListener {
    
    private GLUT glut = new GLUT();
    private GLU glu = new GLU();
    
    // key and mouse buffer
    public KeyBuffer keyBuffer = new KeyBuffer();
    public MouseBuffer mouseBuffer = new MouseBuffer();
    
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
    public static final int BOX_COUNT = 20;
    public static final float MAGNET_TOLERANCY = 10.0f;
    public Set<Box> boxes = new HashSet<Box>();
    
    // camera
    public static final float CAMERA_STEP = 2.0f;
    public Camera cam = Camera.FREE_CAM;
    public float[] cabinCamPosition = {0, 0, 0}; // rx, ry, rz
    public Vector3f cameraRotation = new Vector3f(0, 0, 1);
    public Vector3f cameraPosition = new Vector3f(0, 80, -200);
    
    // fog
    public boolean fog = true;
    private float[] fogColor = { 0.5f, 0.5f, 0.5f, 1.0f};
    
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
        }
        else
        {
            for(int i = 0; i < grabbedItems; i++)
            {
                Box box = new Box((int)(craneRotation - 5.0 + Math.random() * 10.0), (int)(-64.0 + hookDistance - 5.0 + Math.random() * 10.0));
                boxes.add(box);
            }
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
    
    public void camForward()
    {
        Vector3f direction = new Vector3f(cameraRotation);
        direction.normalize();
        direction.scale(CAMERA_STEP);
        cameraPosition.add(direction);
        checkCamPos();
    }
    
    public void camBackward()
    {
        Vector3f direction = new Vector3f(cameraRotation);
        direction.normalize();
        direction.negate();
        direction.scale(CAMERA_STEP);
        cameraPosition.add(direction);
        checkCamPos();
    }
    
    public void camLeft()
    {
        Transform3D matrix = new Transform3D();
        matrix.rotY(Math.PI / 2.0);
        Vector3f direction = new Vector3f(cameraRotation);
        matrix.transform(direction);
        direction.normalize();
        direction.scale(CAMERA_STEP);
        direction.y = 0;
        cameraPosition.add(direction);
        checkCamPos();
    }
    
    public void camRight()
    {
        Transform3D matrix = new Transform3D();
        matrix.rotY(-Math.PI / 2.0);
        Vector3f direction = new Vector3f(cameraRotation);
        matrix.transform(direction);
        direction.normalize();
        direction.scale(CAMERA_STEP);
        direction.y = 0;
        cameraPosition.add(direction);
        checkCamPos();
    }
    
    public void camUp()
    {
        Vector3f up = new Vector3f(0, CAMERA_STEP, 0);
        cameraPosition.add(up);
        checkCamPos();
    }
    
    public void camDown()
    {
        Vector3f down = new Vector3f(0, -CAMERA_STEP, 0);
        cameraPosition.add(down);
        checkCamPos();
    }
    
    public void mouseDown(float x, float y)
    {
        Transform3D matrix = new Transform3D();
        matrix.rotX(y / 200.0);
        matrix.transform(cameraRotation);
        matrix.rotY(-x / 200.0);
        matrix.transform(cameraRotation);
    }
    
    public void checkCamPos()
    {
        if(cameraPosition.y < 4.5f)
        {
            cameraPosition.y = 4.5f;
        }
        if(cameraPosition.y > 200f)
        {
            cameraPosition.y = 200f;
        }
        if(cameraPosition.x > 400f)
        {
            cameraPosition.x = 400f;
        }
        if(cameraPosition.x < -400f)
        {
            cameraPosition.x = -400f;
        }
        if(cameraPosition.z > 400f)
        {
            cameraPosition.z = 400f;
        }
        if(cameraPosition.z < -400f)
        {
            cameraPosition.z = -400f;
        }
    }
    
    
    
    @Override
    public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        
        // gl set up
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_SMOOTH);
        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        
        gl.glEnable(GL2.GL_MULTISAMPLE);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        gl.glHint(GL2.GL_POINT_SMOOTH, GL2.GL_NICEST);
        gl.glHint(GL2.GL_LINE_SMOOTH, GL2.GL_NICEST);
        gl.glHint(GL2.GL_POLYGON_SMOOTH, GL2.GL_NICEST);
        
        gl.glEnable(GL2.GL_POINT_SMOOTH);
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glEnable(GL2.GL_POLYGON_SMOOTH);
        
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
        
        // logic loop
        int period = 50; // repeat every sec.

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                if(keyBuffer.isPressed(KeyEvent.VK_LEFT))
                {
                    rotateCrane(0.5f);
                }
                if(keyBuffer.isPressed(KeyEvent.VK_RIGHT))
                {
                    rotateCrane(-0.5f);
                }
                if(keyBuffer.isPressed(KeyEvent.VK_UP))
                {
                    moveHook(-0.5f);
                }
                if(keyBuffer.isPressed(KeyEvent.VK_DOWN))
                {
                    moveHook(0.5f);
                }
                if(keyBuffer.isPressed(KeyEvent.VK_U))
                {
                    pullHook(0.5f);
                }
                if(keyBuffer.isPressed(KeyEvent.VK_J))
                {
                    pullHook(-0.5f);
                }
                if(keyBuffer.isPressed(KeyEvent.VK_W))
                {
                    camForward();
                }
                if(keyBuffer.isPressed(KeyEvent.VK_S))
                {
                    camBackward();
                }
                if(keyBuffer.isPressed(KeyEvent.VK_A))
                {
                    camLeft();
                }
                if(keyBuffer.isPressed(KeyEvent.VK_D))
                {
                    camRight();
                }
                int[] point = mouseBuffer.getChange();
                mouseDown(point[0], point[1]);
            }
        }, 100, period);
        
        // redraw scene periodically
        FPSAnimator animator = new FPSAnimator(glad, 60);
        animator.start();
    }
    

    @Override
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
   public void display(GLAutoDrawable glad) { 
        
        GL2 gl = glad.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        // fog
        if(fog)
        {
            gl.glFogi(GL2.GL_FOG_MODE, GL2.GL_EXP);
            gl.glFogfv(GL2.GL_FOG_COLOR, fogColor, 0);
            gl.glFogf(GL2.GL_FOG_DENSITY, 0.005f);
            gl.glHint(GL2.GL_FOG_HINT, GL2.GL_NICEST);
            gl.glFogf(GL2.GL_FOG_START, 1.0f);
            gl.glFogf(GL2.GL_FOG_END, 5.0f);
            gl.glEnable(GL2.GL_FOG);
        }
        else
        {
            gl.glDisable(GL2.GL_FOG);
        }
        
        // cameras
        gl.glLoadIdentity();
        switch(cam)
        {
            case FREE_CAM:
                glu.gluLookAt(cameraPosition.x, cameraPosition.y, cameraPosition.z, cameraPosition.x + cameraRotation.x, cameraPosition.y + cameraRotation.y, cameraPosition.z + cameraRotation.z, 0, 1, 0);
                break;
            case HOOK_CAM:
                Transform3D matrix = new Transform3D();
                matrix.rotY((craneRotation + 90.0) / 180.0 * Math.PI);
                Vector3f camPos = new Vector3f(0, 120, -64 + hookDistance + 1);
                matrix.transform(camPos);
                glu.gluLookAt(camPos.x, 120, camPos.z, camPos.x, 0, camPos.z, camPos.x, 0, camPos.z);
                break;
            default:
                Transform3D m = new Transform3D();
                m.rotY((craneRotation + 90.0) / 180.0 * Math.PI);
                Vector3f pos = new Vector3f(0, 114, -12);
                m.transform(pos);
                Vector3f rot = new Vector3f(cameraRotation);
                m.rotY((craneRotation - 90.0) / 180.0 * Math.PI);
                m.transform(rot);
                rot.normalize();
                glu.gluLookAt(pos.x, 115, pos.z, pos.x + rot.x, 115 + rot.y, pos.z + rot.z, 0, 1, 0);
        }
        
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
        for(Box box: boxes)
        {
            gl.glRotatef(box.rotation, 0, 1, 0);
            gl.glTranslatef(box.position, 2, 0);
            drawBox(gl);
            gl.glTranslatef(-box.position, -2, 0);
            gl.glRotatef(-box.rotation, 0, 1, 0);
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
        
    }
    

    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int width, int height) {
        GL2 gl = glad.getGL().getGL2();
        gl.glViewport(x, y, width, height);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50, (float)width/height, 1, 5000);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
    
    private void doLighting( GL2 gl )
    {
        float[] lightPos = {0, 250, 0, 1};
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        float[] noAmbient ={ 0.1f, 0.1f, 0.1f, 1f }; // low ambient light
        float[] spec = { 0.5f, 0.1f, 0f, 1f }; // low ambient light
        float[] diffuse ={ 0.5f, 0.5f, 0.5f, 1f };

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
        float spot_diffuse[] =  {0.6f,0.6f,0.2f,1.0f };
        float spot_specular[] =  {1.0f,1.0f,0.2f,1.0f };
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
