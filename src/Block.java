public class Block {
    private Point3[] points;
    private Point3 position;
    public Block(double x, double y, double z, double size) {
        points = new Point3[] {
            new Point3(x + -size, y + -size, z + -size),
            new Point3(x + -size, y + size, z + -size),
            new Point3(x + size, y + -size, z + -size),
            new Point3(x + size, y + size, z + -size),
            new Point3(x + -size, y + -size, z + size),
            new Point3(x + -size, y + size, z + size),
            new Point3(x + size, y + -size, z + size),
            new Point3(x + size, y + size, z + size)
        };
        position = new Point3(x, y, z);
    }

    public Point3[] getPoints() {
        return this.points;
    }

    public Point3 getPosition() {
        return this.position;
    }

    public void setPosition(Point3 position) {
        this.position = position;
    }

    public double getPositionX() {
        return this.position.getX();
    }

    public double getPositionY() {
        return this.position.getY();
    }

    public double getPositionZ() {
        return this.position.getZ();
    }

    public void setPositionX(double x) {
        this.position.setX(x);
    }

    public void setPositionY(double y) {
        this.position.setY(y);;
    }

    public void setPositionZ(double z) {
        this.position.setZ(z);;
    }

    public void rotateX(double a) {
        for(int i = 0; i < 8; i++) {
            double y = points[i].getY() - position.getY();
            double z = points[i].getZ() - position.getZ();
            points[i].setZ(z * Math.cos(a) - y * Math.sin(a) + position.getZ());
            points[i].setY(z * Math.sin(a) + y * Math.cos(a) + position.getY());
        }
    }

    public void rotateY(double a) {
        for(int i = 0; i < 8; i++) {
            double x = points[i].getX() - position.getX();
            double z = points[i].getZ() - position.getZ();
            points[i].setX(x * Math.cos(a) - z * Math.sin(a) + position.getX());
            points[i].setZ(x * Math.sin(a) + z * Math.cos(a) + position.getZ());
        }
    }

    public void rotateZ(double a) {
        for(int i = 0; i < 8; i++) {
            double x = points[i].getX() - position.getX();
            double y = points[i].getY() - position.getY();
            points[i].setY(y * Math.cos(a) - x * Math.sin(a) + position.getY());
            points[i].setX(y * Math.sin(a) + x * Math.cos(a) + position.getX());
        }
    }
}
