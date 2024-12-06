/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lista;

/**
 *
 * @author royum
 */
public class Cancion {
    private String titulo;
    private String artista;
    private int duracion;
    private String ruta;
    private String tipoMusica;
    private byte[] imagen; // Imagen como arreglo de bytes

    public Cancion(String titulo, String artista, int duracion, String ruta, String tipoMusica, byte[] imagen) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
        this.ruta = ruta;
        this.tipoMusica = tipoMusica;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getRuta() {
        return ruta;
    }

    public String getTipoMusica() {
        return tipoMusica;
    }

    public byte[] getImagen() {
        return imagen;
    }
}