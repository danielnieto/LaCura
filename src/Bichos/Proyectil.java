/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bichos;


import org.niktin.recursos.Recurso;
import org.niktin.recursos.Textura;
import org.niktin.recursos.Vacio;

/**
 *
 * @author Daniel
 */
public class Proyectil extends Recurso{
    public float velocidadDeMovimiento = 1f;

    Proyectil(Textura tex) {
        super(tex);
    }

    public enum Bando{bueno,malo};
    public Bando bando;
    public double puntosAtaque=30;
    public double factorRandomAtaque=.3;

    public Proyectil(Textura tex, int numX, int numY){
        super(tex,numX,numY);

    }


protected boolean colisionaConAlgunEnemigo() {
        if(bando==Bando.bueno){
        for(Ser enemigo: Bichos.malos){
            for(Vacio colisionaObjetivo:enemigo.listaColisiones){
                if(colisionaCon(colisionaObjetivo))
                    return true;

            }
        }

        }else if(bando==Bando.malo){
        for(Ser enemigo: Bichos.buenos){
            for(Vacio colisionaObjetivo:enemigo.listaColisiones){
                if(colisionaCon(colisionaObjetivo))
                    return true;

            }
        }

        }
        return false;
    }

protected Ser conQueEnemigoColisiona() {
        if(bando==Bando.bueno){
        for(Ser enemigo: Bichos.malos){
            for(Vacio colisionaObjetivo:enemigo.listaColisiones){
                if(colisionaCon(colisionaObjetivo))
                    return enemigo;

            }
        }

        }else if(bando==Bando.malo){
        for(Ser enemigo: Bichos.buenos){
            for(Vacio colisionaObjetivo:enemigo.listaColisiones){
                if(colisionaCon(colisionaObjetivo))
                    return enemigo;

            }
        }

        }
        return null;
    }

    void actualizarEstado() {
        if(bando==bando.bueno){
            asignarX(obtenerX()+velocidadDeMovimiento);
        }else{
            asignarX(obtenerX()-velocidadDeMovimiento);
           
        }

    }


}

