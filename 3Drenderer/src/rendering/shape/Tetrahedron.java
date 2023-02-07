package rendering.shape;

import rendering.point.MyPoint;

import java.awt.*;

public class Tetrahedron {
    private MyPolygon[] polygons;
    private Color color;

    public Tetrahedron(Color color, MyPolygon... polygons)
    {
        this.color=color;
        this.polygons=polygons;
        this.setPolygonColor();
    }
    public Tetrahedron(MyPolygon... polygons)
    {
        this.color=Color.yellow;
        this.polygons=polygons;
        //this.setPolygonColor();
    }
    public void rotate(boolean CW,double xDegrees,double yDegrees, double zDegrees)
    {
        for(MyPolygon p : this.polygons)
        {
            p.rotate(CW,xDegrees,yDegrees,zDegrees);
        }
        this.sortPolygon();
    }
    public void render(Graphics g)
    {
      for(MyPolygon poly: this.polygons)
      {
          poly.render(g);
      }
    }

    private  void sortPolygon()
    {
        MyPolygon.sortPolygons(this.polygons);
    }

    private void setPolygonColor()
    {
        for(MyPolygon poly : this.polygons)
        {
            poly.setColor(this.color);
        }
    }
}
