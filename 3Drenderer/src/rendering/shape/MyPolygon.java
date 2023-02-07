package rendering.shape;

import rendering.point.MyPoint;
import rendering.point.PointConverter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyPolygon {

    private MyPoint[] points;
    private Color color;

    public MyPolygon(Color color,MyPoint... points)
    {   this.color=color;
        this.points= new MyPoint[points.length];
        for(int i=0;i<points.length;i++)
        {
            MyPoint p = points[i];
            this.points[i]= new MyPoint(p.x,p.y,p.z);
        }
    }
    public MyPolygon(MyPoint... points)
    {   this.color=Color.WHITE;
        this.points= new MyPoint[points.length];
        for(int i=0;i<points.length;i++)
        {
            MyPoint p = points[i];
            this.points[i]= new MyPoint(p.x,p.y,p.z);
        }
    }

    public void render(Graphics g)
    {
        Polygon poly = new Polygon();

        for(int i=0;i< this.points.length;i++)
        {
        Point p1 = PointConverter.convertPoint(this.points[i]);
        poly.addPoint(p1.x,p1.y);
        }
        g.setColor(this.color);
        g.fillPolygon(poly);
    }
    public void rotate(boolean CW,double xDegrees,double yDegrees,double zDegrees){
        for(MyPoint p : points)
        {
            PointConverter.rotateAxisX(p,CW,xDegrees);
            PointConverter.rotateAxisY(p,CW,yDegrees);
            PointConverter.rotateAxisZ(p,CW,zDegrees);
        }
    }
    public double getAverageX(){
        double sum=0;
        for(MyPoint p : this.points)
        {
            sum+=p.x;
        }
        return sum/this.points.length;
    }
    public static MyPolygon[] sortPolygons(MyPolygon[] polygons)
    {
        List<MyPolygon> polygonsList = new ArrayList<>();

        for(MyPolygon poly : polygons)
        {
            polygonsList.add(poly);
        }

        Collections.sort(polygonsList,new Comparator<>() {
            public int compare(MyPolygon p1, MyPolygon p2) {
                return p2.getAverageX() - p1.getAverageX() < 0 ? 1 : -1;
            }
        });

        for(int i=0 ;i< polygons.length;i++)
        {
            polygons[i]=polygonsList.get(i);
        }
        return polygons;
    }

    public void setColor(Color color)
    {
        this.color=color;
    }
}
