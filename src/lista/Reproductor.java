/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lista;

import javazoom.jl.player.Player;
import javax.swing.*;
import java.awt.*;
import java.io.*;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;

/**
 *
 * @author royum    
 */
public class Reproductor extends JFrame{

      private ListaEnlazada listaCanciones;
    private int reproduciendo = -1; 
    private JLabel lblTitulo, lblArtista, lblDuracion, lblImagen;
    private Player player;

    public Reproductor() {
        listaCanciones = new ListaEnlazada();

        setTitle("Reproductor Musical");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Estilo general
        Font fuenteDetalles = new Font("Arial", Font.PLAIN, 14);
        Font fuenteBotones = new Font("Arial", Font.BOLD, 12);

       
        JPanel panelDetalles = new JPanel();
        panelDetalles.setLayout(new BoxLayout(panelDetalles, BoxLayout.Y_AXIS));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTitulo = new JLabel("Titulo: ");
        lblTitulo.setFont(fuenteDetalles);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblArtista = new JLabel("Artista: ");
        lblArtista.setFont(fuenteDetalles);
        lblArtista.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDuracion = new JLabel("Duración: ");
        lblDuracion.setFont(fuenteDetalles);
        lblDuracion.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelDetalles.add(lblImagen);
        panelDetalles.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado
        panelDetalles.add(lblTitulo);
        panelDetalles.add(lblArtista);
        panelDetalles.add(lblDuracion);

        add(panelDetalles, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnAgregar = new JButton("Agregar Cancion");
        btnAgregar.setFont(fuenteBotones);

        JButton btnListar = new JButton("Listar Canciones");
        btnListar.setFont(fuenteBotones);

        JButton btnPlay = new JButton("Play");
        btnPlay.setFont(fuenteBotones);

        JButton btnStop = new JButton("Stop");
        btnStop.setFont(fuenteBotones);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnListar);
        panelBotones.add(btnPlay);
        panelBotones.add(btnStop);
       

        add(panelBotones, BorderLayout.SOUTH);

        // Agregar acciones
        btnAgregar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos MP3", "mp3"));
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                try {
                    AudioFile audioFile = AudioFileIO.read(archivoSeleccionado);
                    Tag tag = audioFile.getTag();
                    int duracion = audioFile.getAudioHeader().getTrackLength();

                    String titulo = tag != null ? tag.getFirst(FieldKey.TITLE) : "Desconocido";
                    String artista = tag != null ? tag.getFirst(FieldKey.ARTIST) : "Desconocido";

                    byte[] imagenBytes = null;
                    Artwork artwork = tag != null ? tag.getFirstArtwork() : null;
                    if (artwork != null) {
                        imagenBytes = artwork.getBinaryData();
                    }

                    Cancion nuevaCancion = new Cancion(
                            titulo.isEmpty() ? "Desconocido" : titulo,
                            artista.isEmpty() ? "Desconocido" : artista,
                            duracion,
                            archivoSeleccionado.getAbsolutePath(),
                            "Desconocido",
                            imagenBytes
                    );
                    listaCanciones.agregarCancion(nuevaCancion);
                    JOptionPane.showMessageDialog(this, "Canción agregada: " + titulo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al leer los metadatos del archivo.");
                }
            }
        });

        btnListar.addActionListener(e -> {
            StringBuilder lista = new StringBuilder();
            Nodo actual = listaCanciones.getCabeza();
            int indice = 1;
            while (actual != null) {
                Cancion cancion = actual.getCancion();
                lista.append(indice).append(". ").append(cancion.getTitulo()).append(" - ")
                        .append(cancion.getArtista()).append("\n");
                actual = actual.getSiguiente();
                indice++;
            }
            JOptionPane.showMessageDialog(this, lista.length() > 0 ? lista.toString() : "No hay canciones en la lista.");
        });

        btnPlay.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Ingrese el número de la canción:");
            try {
                int indice = Integer.parseInt(input);
                Cancion cancion = listaCanciones.obtenerCancion(indice);
                if (cancion != null) {
                    actualizarDetalles(cancion);
                    reproducirCancion(cancion);
                } else {
                    JOptionPane.showMessageDialog(this, "Número inválido.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.");
            }
        });

        btnStop.addActionListener(e -> detenerCancion());
       
    }

    private void actualizarDetalles(Cancion cancion) {
        lblTitulo.setText("Título: " + cancion.getTitulo());
        lblArtista.setText("Artista: " + cancion.getArtista());
        lblDuracion.setText("Duración: " + cancion.getDuracion() + " segundos");

        if (cancion.getImagen() != null) {
            ImageIcon icon = new ImageIcon(cancion.getImagen());
            lblImagen.setIcon(new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        } else {
            lblImagen.setIcon(null);
        }
    }

    private void reproducirCancion(Cancion cancion) {
        detenerCancion();
        try {
            FileInputStream fis = new FileInputStream(cancion.getRuta());
            player = new Player(fis);
            new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al reproducir la canción.");
                }
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo reproducir el archivo.");
        }
    }

    private void detenerCancion() {
        if (player != null) {
            player.close();
            player = null;
        }
    }

}