package rendering;

import rendering.point.MyPoint;
import rendering.shape.MyPolygon;
import rendering.shape.Tetrahedron;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable{
    private final long serialVersionUID=1L;
    private Thread thread;
    private JFrame frame;
    private static String title="3D renderer";
    public static final int width=800;
    public static final int height=600;
    private static boolean running =false;
    private Tetrahedron tetra;

    public Display()
    {
        this.frame = new JFrame();
        Dimension size = new Dimension(width,height);
        this.setPreferredSize(size);
    }

    public synchronized void start()
    {
        running= true;
        this.thread = new Thread(this,"rendering.Display");
        this.thread.start();
    }
    public synchronized void stop()
    {   running= false;

        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run()
    {   long lastTime = System.nanoTime();
        long timer= System.currentTimeMillis();
        final double ns=1000000000.0 /60;
        double progress = 0; // progress for next update
        int frames=0; // to keep track of the frames
        //cube();
       //cuboid();
        //Pyramid();
        Prism();
        //Octahedron();
        while(running) {
            long now = System.nanoTime();
            progress += (now - lastTime) / ns;
            lastTime = now;
            while (progress >= 1) {
                update();
                progress--;
                render();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000)
            {
                timer+=1000;
                this.frame.setTitle(title+ " | "+ frames+ " fps");
                frames=0;
            }
        }
        stop();
    }



    public void cube()
    {   int s=100;
       MyPoint p1=new MyPoint(s/2,-s/2,-s/2);
       MyPoint p2=new MyPoint(s/2,s/2,-s/2);
       MyPoint p3=new MyPoint(s/2,s/2,s/2);
       MyPoint p4=new MyPoint(s/2,-s/2,s/2);
       MyPoint p5=new MyPoint(-s/2,-s/2,-s/2);
       MyPoint p6=new MyPoint(-s/2,s/2,-s/2);
       MyPoint p7=new MyPoint(-s/2,s/2,s/2);
       MyPoint p8=new MyPoint(-s/2,-s/2,s/2);
        this.tetra = new Tetrahedron(
                new MyPolygon(Color.BLUE,p5,p6,p7,p8), // back
                new MyPolygon(Color.WHITE,p1,p2,p6,p5),  // bottom
                new MyPolygon(Color.yellow,p1,p5,p8,p4),  // left
                new MyPolygon(Color.GREEN,p2,p6,p7,p3), // right
                new MyPolygon(Color.ORANGE,p4,p3,p7,p8),//top
                new MyPolygon(Color.RED ,p1,p2,p3,p4) // front
                ); // front

    }
    public void cuboid()
    {
        int l=100,b=200;
        MyPoint p1 = new MyPoint(b/2,-l/2,-b/2);
        MyPoint p2 = new MyPoint(b/2,l/2,-b/2);
        MyPoint p3 = new MyPoint(b/2,l/2,b/2);
        MyPoint p4 = new MyPoint(b/2,-l/2,b/2);
        MyPoint p5 = new MyPoint(-b/2,-l/2,-b/2);
        MyPoint p6 = new MyPoint(-b/2,l/2,-b/2);
        MyPoint p7 = new MyPoint(-b/2,l/2,b/2);
        MyPoint p8 = new MyPoint(-b/2,-l/2,b/2);
        this.tetra= new Tetrahedron(new MyPolygon(Color.BLUE,p5,p6,p7,p8), // back
                new MyPolygon(Color.WHITE,p1,p2,p6,p5),  // bottom
                new MyPolygon(Color.yellow,p1,p5,p8,p4),  // left
                new MyPolygon(Color.GREEN,p2,p6,p7,p3), // right
                new MyPolygon(Color.ORANGE,p4,p3,p7,p8),//top
                new MyPolygon(Color.RED ,p1,p2,p3,p4)); // front
    }

    public void Pyramid()
    {   double s=200;
        double d=Math.sqrt(s*s-(s*s)/4);
        MyPoint p1 = new MyPoint(0,0,d/2);
        MyPoint p2 = new MyPoint(-s/2,-s/2,-d/2);
        MyPoint p3 = new MyPoint(-s/2,s/2,-d/2);
        MyPoint p4 = new MyPoint(s/2,-s/2,-d/2);
        MyPoint p5 = new MyPoint(s/2,s/2,-d/2);

        this.tetra= new Tetrahedron(
                new MyPolygon(Color.RED,p2,p3,p5,p4),
                new MyPolygon(Color.yellow,p4,p1,p5),
                new MyPolygon(Color.blue,p5,p1,p3),
                new MyPolygon(Color.green,p3,p1,p2),
                new MyPolygon(new Color(120,140,200),p2,p1,p4));
    }
    public void Prism()
    {
        // this is not a prism unfortunately
        MyPoint p1 = new MyPoint(100,0,-100);
        MyPoint p2 = new MyPoint(-50,50*Math.sqrt(3),-100);
        MyPoint p3 = new MyPoint(-50,-50*Math.sqrt(3),-100);
        MyPoint p4 = new MyPoint(0,0,100);

//            double s=300;
//            double d= Math.sqrt(s*s-(s*s)/4);
//            MyPoint p1= new MyPoint(0,0,d/2);
//            MyPoint p2= new MyPoint(d/2,-s/2,-d/2);
//            MyPoint p3= new MyPoint(d/2,s/2,-d/2);
//            MyPoint p4= new MyPoint(-d/2,0,d/2);
//
            this.tetra= new Tetrahedron(new MyPolygon(Color.RED,p2,p4,p3), // bottom
                    new MyPolygon(Color.GREEN,p2,p1,p3),
                    new MyPolygon(Color.yellow,p3,p1,p4),
                    new MyPolygon(Color.WHITE,p4,p1,p2));
    }
    private void render() {
         BufferStrategy bs = this.getBufferStrategy();
         if(bs==null)
         {
             this.createBufferStrategy(3);
             return;
         }
         Graphics g = bs.getDrawGraphics(); // drawing stuff in our background
         g.setColor(Color.BLACK); // setting the background color as black
         g.fillRect(0,0,width,height); // setting the graphic window parameters
//         g.setColor(new Color(200,150,150)); // a simple rectangle it is and it is red in color
//         g.fillRect(50,100,200,300);

//            arr1[0]= new MyPoint(0,20,80);
//            arr1[1]= new MyPoint(0,60,80);
//            arr1[2]= new MyPoint(0,20,20);
//            arr1[3]= new MyPoint(0,60,20);



//         MyPolygon poly1 = new MyPolygon(arr1);
          //  g.setColor(Color.RED);
      //      poly.render(g);
//         g.setColor(Color.ORANGE);
//         poly1.render(g);

        tetra.render(g);
         g.dispose();
         bs.show();
    }
    private void Octahedron() {
    }
    private void update()
    {
        this.tetra.rotate(true,0,0,1);
    }

    public static void main(String[] args){
        Display display = new Display();
        display.frame.setTitle(title);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setResizable(false);
        display.frame.setVisible(true);
        display.start();
    }
}
