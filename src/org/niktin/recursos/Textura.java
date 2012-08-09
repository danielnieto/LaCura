/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.niktin.recursos;

import java.io.FileInputStream;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Daniel
 */
public class Textura {
    private boolean cargado=false;
    private Texture textura;

    public Textura(String ruta){
         try {
            textura = TextureLoader.getTexture("PNG",new FileInputStream(ruta));
            cargado=true;
        } catch (IOException ex) {

        }

    }

    public float obtenerAltura(){
        return textura.getHeight();

    }

    public float obtenerAnchura(){
        return textura.getWidth();

    }

    public int obtenerAnchuraDeImagen(){
        return textura.getImageWidth();
    }

    public int obtenerAlturaDeImagen(){
        return textura.getImageHeight();
    }

    public int obtenerAnchuraDeTextura(){
        return textura.getTextureWidth();
    }

    public int obtenerAlturaDeTextura(){
        return textura.getTextureHeight();
    }

    public void vincular(){
        textura.bind();
    }

    public boolean tieneAlpha(){
        return textura.hasAlpha();
    }

    public boolean cargaCompleta(){
        return cargado;
    }
}
