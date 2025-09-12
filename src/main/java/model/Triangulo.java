package model;
public class Triangulo {
    private float base;
    private float altura;
    private float area;

    public Triangulo() {
    }

    public Triangulo(float base, float altura) {
        this.base = base;
        this.altura = altura;
    }

    public float getBase() {
        return base;
    }

    public void setBase(float base) {
        this.base = base;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float base, float altura) {
        this.area = (base * altura ) / 2;
    }

    @Override
    public String toString() {
        return  "Base  : " + this.getBase() +
                "\nAltura: " + this.getAltura()+
                "\nÁrea  : " + this.getArea();
    }
    
}
