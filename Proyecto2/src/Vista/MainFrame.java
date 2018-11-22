/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.Controlador;
import MapaDecorator.TipoVista;
import MapaDecorator.Marker;
import Controlador.DTOConsulta;
import Controlador.DTOInterfaz;
import Controlador.TipoIdentificador;
import MapaDecorator.DataMapa;
import MapaDecorator.DecoratorCenter;
import MapaDecorator.Mapa;
import MapaDecorator.DecoratorMarkers;
import MapaDecorator.DecoratorZoom;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author ayma-93
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     * @throws java.sql.SQLException
     */
    
    Controlador controller;
    DTOInterfaz dtoInterfaz_Entrada;
    DTOInterfaz dtoInterfaz_Salida;
    DTOConsulta dtoConsulta;
    
    //Definir modelo de datos para las listas de la interfaz
    
    DefaultListModel modProvincia;
    DefaultListModel modCanton;
    DefaultListModel modDistrito;
    DefaultListModel modTipoLesion;
    DefaultListModel modRolAfectado;
    DefaultListModel modEdadQuinquenal;
    
   
    
    
    public MainFrame() throws SQLException, ClassNotFoundException {
        initComponents();
        this.setLocation(40, 40); 
        descargarMapa(TipoVista.PROVINCIA,false,new ArrayList<>());
        this.rbSexo_Indicador.setSelected(true);
        
        this.controller = new Controlador();
        this.dtoInterfaz_Entrada = new DTOInterfaz();
        this.dtoInterfaz_Salida = new DTOInterfaz();
        this.dtoConsulta = new DTOConsulta();
        
        inicializarDatos();
        
        
    }
    
    public void inicializarDatos() throws SQLException{
        ResultSet rs = null;
        
        this.modProvincia = new DefaultListModel();
        this.modCanton = new DefaultListModel();
        this.modDistrito = new DefaultListModel();
        this.modTipoLesion = new DefaultListModel();
        this.modRolAfectado = new DefaultListModel();
        this.modEdadQuinquenal = new DefaultListModel();
    
        this.listProvincias.setModel(modProvincia);
        this.listCantones.setModel(modCanton);
        this.listDistritos.setModel(modDistrito);
        this.listTipoLesion_Grafica.setModel(modTipoLesion);
        this.listRolAfectado_Grafica.setModel(modRolAfectado);
        this.listEdadQuinquenal_Grafica.setModel(modEdadQuinquenal);
        
        modProvincia.clear();
        modCanton.clear();
        modDistrito.clear();
        modTipoLesion.clear();
        modRolAfectado.clear();
        modEdadQuinquenal.clear();
        
        this.cbTipoAfectado_Dashboard.removeAllItems();
        this.cbTipoLesion_Dashboard.removeAllItems();
        this.cbEdadQuinquenal_Dashboard.removeAllItems();
        
        this.cbTipoAfectado_Dashboard.addItem("-");
        this.cbTipoLesion_Dashboard.addItem("-");
        this.cbEdadQuinquenal_Dashboard.addItem("-");
        
        
        dtoInterfaz_Entrada = controller.getListaProvincias(dtoInterfaz_Salida);        
        rs = dtoInterfaz_Entrada.getRs();
        while(rs.next()){
            modProvincia.addElement(rs.getString("Provincia").trim());
        }
        
        dtoInterfaz_Entrada = controller.getListaTipoLesion(dtoInterfaz_Salida);
        rs = dtoInterfaz_Entrada.getRs();
        while(rs.next()){
            this.cbTipoLesion_Dashboard.addItem(rs.getString("TipoLesion").trim());
            modTipoLesion.addElement(rs.getString("TipoLesion").trim());
        }
        
        dtoInterfaz_Entrada = controller.getListaRolAfectado(dtoInterfaz_Salida);
        rs = dtoInterfaz_Entrada.getRs();
        while(rs.next()){
            this.cbTipoAfectado_Dashboard.addItem(rs.getString("RolAfectado").trim());
            modRolAfectado.addElement(rs.getString("RolAfectado").trim());
        }
        
        dtoInterfaz_Entrada = controller.getListaEdadQuinquenal(dtoInterfaz_Salida);
        rs = dtoInterfaz_Entrada.getRs();
        while(rs.next()){
            this.cbEdadQuinquenal_Dashboard.addItem(rs.getString("EdadQuinquenal").trim());
            modEdadQuinquenal.addElement(rs.getString("EdadQuinquenal").trim());
        }
        
        
        
    }
    
    private void descargarMapa(TipoVista vista,boolean keepZoom,ArrayList<Marker> marcadores){
        
        DataMapa MapaConsulta1 = new Mapa(vista,keepZoom,marcadores);
        MapaConsulta1 = new DecoratorMarkers(MapaConsulta1);
        MapaConsulta1 = new DecoratorCenter(MapaConsulta1);
        MapaConsulta1 = new DecoratorZoom(MapaConsulta1);
        
        
        
        try {
        URL url = new URL(MapaConsulta1.getLink());
        HttpURLConnection httpcon = (HttpURLConnection) url.openConnection(); 
        httpcon.addRequestProperty("User-Agent", ""); 
        BufferedImage image = ImageIO.read(httpcon.getInputStream());
        File outputfile = new File("mapa.jpg");
        ImageIO.write(image, "jpg", outputfile);   
       } 
       catch (Exception e) {
        e.printStackTrace();
       }
       
        
       //Carga el mapa
       
       BufferedImage img = null;
        try {
            img = ImageIO.read(new File("mapa.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
       Image dimg = img.getScaledInstance(this.labelMapa.getWidth(), this.labelMapa.getHeight(),
        Image.SCALE_SMOOTH);
       this.labelMapa.setIcon(new ImageIcon(dimg));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        panelPrincipal = new javax.swing.JTabbedPane();
        panelDashboard = new javax.swing.JPanel();
        panelAdicional = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listProvincias = new javax.swing.JList<>();
        cbAños = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCantones = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        listDistritos = new javax.swing.JList<>();
        cbSexo_Dashboard = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbTipoAfectado_Dashboard = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbTipoLesion_Dashboard = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbEdadQuinquenal_Dashboard = new javax.swing.JComboBox<>();
        btnProcesarDashboard1 = new javax.swing.JButton();
        btnProcesarDashboard = new javax.swing.JButton();
        btnProcesarDashboard2 = new javax.swing.JButton();
        labelMapa = new javax.swing.JLabel();
        panelIndicador = new javax.swing.JPanel();
        panelBotones_Indicador = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        rbSexo_Indicador = new javax.swing.JRadioButton();
        rbTipoAfectado_Identificador = new javax.swing.JRadioButton();
        rbTipoLesion_Indicador = new javax.swing.JRadioButton();
        rbEdadQuinquenal_Indicador = new javax.swing.JRadioButton();
        cbAñosConsulta2 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        btnGenerarGrafica = new javax.swing.JButton();
        btnProcesarDashboard3 = new javax.swing.JButton();
        btnGenerarGrafica1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listRolAfectado_Grafica = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        listSexo_Grafica = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        listTipoLesion_Grafica = new javax.swing.JList<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        listEdadQuinquenal_Grafica = new javax.swing.JList<>();
        panelGrafico = new javax.swing.JPanel();
        panelLibre = new javax.swing.JPanel();
        btnProcesarDashboard4 = new javax.swing.JButton();
        PanelPersonal = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1297, 708));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setMaximumSize(new java.awt.Dimension(1297, 708));
        jPanel2.setMinimumSize(new java.awt.Dimension(1297, 708));
        jPanel2.setPreferredSize(new java.awt.Dimension(1297, 708));

        panelPrincipal.setBackground(new java.awt.Color(51, 51, 51));

        panelDashboard.setBackground(new java.awt.Color(51, 51, 51));

        panelAdicional.setBackground(new java.awt.Color(51, 51, 51));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Localización Geografica");

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Canton(es)");

        listProvincias.setBackground(new java.awt.Color(102, 102, 102));
        listProvincias.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listProvincias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listProvinciasMouseClicked(evt);
            }
        });
        listProvincias.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listProvinciasValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listProvincias);

        cbAños.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2012", "2013", "2014", "2012 - 2013", "2012 - 2014", "2013 - 2014" }));
        cbAños.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAñosActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Año(s)");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Distrito(s)");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Provincia(s)");

        listCantones.setBackground(new java.awt.Color(102, 102, 102));
        listCantones.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listCantones.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listCantonesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listCantones);

        listDistritos.setBackground(new java.awt.Color(102, 102, 102));
        listDistritos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listDistritos);

        cbSexo_Dashboard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Hombre", "Mujer", "Desconocido" }));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sexo");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Adicionales");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tipo de Afectado");

        cbTipoAfectado_Dashboard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Ciclista", "Conductor", "Dueño de Propiedad", "Motociclista", "Pasajero Bicicleta", "Pasajero Bus o MicroBus", "Pasajero Carro", "Pasajero Moto", "Peaton", "Otro" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tipo de Lesión");

        cbTipoLesion_Dashboard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Ileso", "Herido Leve ", "Herido Grave", "Muerte" }));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Edad Quinquenal");

        cbEdadQuinquenal_Dashboard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "De 0 a 4 años", "De 5 a 9 años", "De 10 a 14 años", "De 15 a 19 años", "De 20 a 24 años", "De 25 a 29 años", "De 30 a 34 años", "De 35 a 39 años", "De 40 a 44 años", "De 45 a 49 años", "De 50 a 54 años", "De 55 a 59 años", "De 60 a 64 años", "De 65 a 69 años", "De 70 a 74 años", "Mayor a 75 años", "Desconocida" }));

        btnProcesarDashboard1.setBackground(new java.awt.Color(0, 153, 204));
        btnProcesarDashboard1.setLabel("LIMPIAR CONSULTA");

        btnProcesarDashboard.setBackground(new java.awt.Color(0, 153, 204));
        btnProcesarDashboard.setText("GENERAR CONSULTA");
        btnProcesarDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarDashboardActionPerformed(evt);
            }
        });

        btnProcesarDashboard2.setBackground(new java.awt.Color(0, 153, 204));
        btnProcesarDashboard2.setText("CERRAR SISTEMA");
        btnProcesarDashboard2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarDashboard2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(cbSexo_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(cbTipoAfectado_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(cbEdadQuinquenal_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(cbTipoLesion_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbAños, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProcesarDashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnProcesarDashboard2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProcesarDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(17, 17, 17)
                .addComponent(cbAños, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbSexo_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTipoAfectado_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTipoLesion_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbEdadQuinquenal_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnProcesarDashboard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProcesarDashboard1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProcesarDashboard2)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout panelAdicionalLayout = new javax.swing.GroupLayout(panelAdicional);
        panelAdicional.setLayout(panelAdicionalLayout);
        panelAdicionalLayout.setHorizontalGroup(
            panelAdicionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdicionalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAdicionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(panelAdicionalLayout.createSequentialGroup()
                        .addComponent(labelMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 927, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        panelAdicionalLayout.setVerticalGroup(
            panelAdicionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdicionalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAdicionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelMapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelDashboardLayout = new javax.swing.GroupLayout(panelDashboard);
        panelDashboard.setLayout(panelDashboardLayout);
        panelDashboardLayout.setHorizontalGroup(
            panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelAdicional, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelDashboardLayout.setVerticalGroup(
            panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelAdicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelPrincipal.addTab("Dashboard", panelDashboard);

        panelIndicador.setBackground(new java.awt.Color(51, 51, 51));

        panelBotones_Indicador.setBackground(new java.awt.Color(51, 51, 51));

        jLabel10.setBackground(new java.awt.Color(51, 51, 51));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Seleccione el tipo de indicador :");

        rbSexo_Indicador.setBackground(new java.awt.Color(51, 51, 51));
        rbSexo_Indicador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbSexo_Indicador.setForeground(new java.awt.Color(255, 255, 255));
        rbSexo_Indicador.setText("Sexo");
        rbSexo_Indicador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSexo_IndicadorActionPerformed(evt);
            }
        });

        rbTipoAfectado_Identificador.setBackground(new java.awt.Color(51, 51, 51));
        rbTipoAfectado_Identificador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbTipoAfectado_Identificador.setForeground(new java.awt.Color(255, 255, 255));
        rbTipoAfectado_Identificador.setText("Rol de Afectado");
        rbTipoAfectado_Identificador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTipoAfectado_IdentificadorActionPerformed(evt);
            }
        });

        rbTipoLesion_Indicador.setBackground(new java.awt.Color(51, 51, 51));
        rbTipoLesion_Indicador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbTipoLesion_Indicador.setForeground(new java.awt.Color(255, 255, 255));
        rbTipoLesion_Indicador.setText("Tipo de Lesión");
        rbTipoLesion_Indicador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTipoLesion_IndicadorActionPerformed(evt);
            }
        });

        rbEdadQuinquenal_Indicador.setBackground(new java.awt.Color(51, 51, 51));
        rbEdadQuinquenal_Indicador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbEdadQuinquenal_Indicador.setForeground(new java.awt.Color(255, 255, 255));
        rbEdadQuinquenal_Indicador.setText("Edad Quinquenal");
        rbEdadQuinquenal_Indicador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEdadQuinquenal_IndicadorActionPerformed(evt);
            }
        });

        cbAñosConsulta2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2012", "2013", "2014", "2012 - 2013", "2012 - 2014", "2013 - 2014" }));
        cbAñosConsulta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAñosConsulta2ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Año(s)");

        btnGenerarGrafica.setBackground(new java.awt.Color(0, 153, 204));
        btnGenerarGrafica.setAutoscrolls(true);
        btnGenerarGrafica.setLabel("LIMPIAR GRÁFICA");
        btnGenerarGrafica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarGraficaActionPerformed(evt);
            }
        });

        btnProcesarDashboard3.setBackground(new java.awt.Color(0, 153, 204));
        btnProcesarDashboard3.setText("CERRAR SISTEMA");
        btnProcesarDashboard3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarDashboard3ActionPerformed(evt);
            }
        });

        btnGenerarGrafica1.setBackground(new java.awt.Color(0, 153, 204));
        btnGenerarGrafica1.setText("GENERAR GRÁFICA");
        btnGenerarGrafica1.setActionCommand("");
        btnGenerarGrafica1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarGrafica1ActionPerformed(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Seleccione el rango de fechas:");

        listRolAfectado_Grafica.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(listRolAfectado_Grafica);

        listSexo_Grafica.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Hombre", "Mujer", "Desconocido" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(listSexo_Grafica);

        listTipoLesion_Grafica.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(listTipoLesion_Grafica);

        listEdadQuinquenal_Grafica.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(listEdadQuinquenal_Grafica);

        javax.swing.GroupLayout panelBotones_IndicadorLayout = new javax.swing.GroupLayout(panelBotones_Indicador);
        panelBotones_Indicador.setLayout(panelBotones_IndicadorLayout);
        panelBotones_IndicadorLayout.setHorizontalGroup(
            panelBotones_IndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotones_IndicadorLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelBotones_IndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotones_IndicadorLayout.createSequentialGroup()
                        .addGroup(panelBotones_IndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGenerarGrafica1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProcesarDashboard3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGenerarGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(cbAñosConsulta2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(rbSexo_Indicador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbTipoLesion_Indicador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rbTipoAfectado_Identificador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addComponent(rbEdadQuinquenal_Indicador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane7))
                .addContainerGap())
        );
        panelBotones_IndicadorLayout.setVerticalGroup(
            panelBotones_IndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotones_IndicadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbAñosConsulta2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbSexo_Indicador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(rbTipoLesion_Indicador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbTipoAfectado_Identificador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rbEdadQuinquenal_Indicador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGenerarGrafica1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGenerarGrafica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProcesarDashboard3)
                .addContainerGap())
        );

        panelGrafico.setBackground(new java.awt.Color(102, 102, 102));
        panelGrafico.setPreferredSize(new java.awt.Dimension(1373, 720));

        javax.swing.GroupLayout panelGraficoLayout = new javax.swing.GroupLayout(panelGrafico);
        panelGrafico.setLayout(panelGraficoLayout);
        panelGraficoLayout.setHorizontalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1022, Short.MAX_VALUE)
        );
        panelGraficoLayout.setVerticalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 583, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelIndicadorLayout = new javax.swing.GroupLayout(panelIndicador);
        panelIndicador.setLayout(panelIndicadorLayout);
        panelIndicadorLayout.setHorizontalGroup(
            panelIndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIndicadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBotones_Indicador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelIndicadorLayout.setVerticalGroup(
            panelIndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIndicadorLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelIndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelBotones_Indicador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(492, Short.MAX_VALUE))
        );

        panelPrincipal.addTab("Indicador de Comportamiento", panelIndicador);

        panelLibre.setBackground(new java.awt.Color(51, 51, 51));

        btnProcesarDashboard4.setBackground(new java.awt.Color(0, 153, 204));
        btnProcesarDashboard4.setText("CERRAR SISTEMA");
        btnProcesarDashboard4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarDashboard4ActionPerformed(evt);
            }
        });

        PanelPersonal.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout PanelPersonalLayout = new javax.swing.GroupLayout(PanelPersonal);
        PanelPersonal.setLayout(PanelPersonalLayout);
        PanelPersonalLayout.setHorizontalGroup(
            PanelPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 964, Short.MAX_VALUE)
        );
        PanelPersonalLayout.setVerticalGroup(
            PanelPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelLibreLayout = new javax.swing.GroupLayout(panelLibre);
        panelLibre.setLayout(panelLibreLayout);
        panelLibreLayout.setHorizontalGroup(
            panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLibreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnProcesarDashboard4, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        panelLibreLayout.setVerticalGroup(
            panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLibreLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnProcesarDashboard4)
                    .addComponent(PanelPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPrincipal.addTab("Libre", panelLibre);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Historial de Accidentes en Costa Rica");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1222, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listCantonesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listCantonesValueChanged
        if (this.listCantones.getSelectedIndices().length > 1){
            this.listDistritos.setEnabled(false);
            this.modDistrito.clear();
        }
        else{
            dtoInterfaz_Salida.setProvincia(this.listProvincias.getSelectedValue());
            dtoInterfaz_Salida.setCanton(this.listCantones.getSelectedValue());
            dtoInterfaz_Entrada = controller.getListaDistritos(dtoInterfaz_Salida);
            ResultSet rs = dtoInterfaz_Entrada.getRs();
            this.modDistrito.clear();
            try {
                while(rs.next()){
                    modDistrito.addElement(rs.getString("Distrito").trim());
                }   } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.listDistritos.setEnabled(true);
            this.listDistritos.clearSelection();
        }
    }//GEN-LAST:event_listCantonesValueChanged

    private void cbAñosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAñosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAñosActionPerformed

    private void listProvinciasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listProvinciasValueChanged
        if (this.listProvincias.getSelectedIndices().length > 1){
            this.listCantones.setEnabled(false);
            this.listDistritos.setEnabled(false);
            this.modCanton.clear();
        }
        else{
            dtoInterfaz_Salida.setProvincia(this.listProvincias.getSelectedValue());
            dtoInterfaz_Entrada = controller.getListaCantones(dtoInterfaz_Salida);
            ResultSet rs = dtoInterfaz_Entrada.getRs();
            this.modCanton.clear();
            try {
                while(rs.next()){
                    modCanton.addElement(rs.getString("Canton").trim());
                }   } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.listCantones.setEnabled(true);
            this.listCantones.clearSelection();
        }
    }//GEN-LAST:event_listProvinciasValueChanged

    private void listProvinciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listProvinciasMouseClicked

    }//GEN-LAST:event_listProvinciasMouseClicked

    private void btnGenerarGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarGraficaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGenerarGraficaActionPerformed

    private void rbSexo_IndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSexo_IndicadorActionPerformed
        this.rbEdadQuinquenal_Indicador.setSelected(false);
        this.rbTipoAfectado_Identificador.setSelected(false);
        this.rbTipoLesion_Indicador.setSelected(false);
    }//GEN-LAST:event_rbSexo_IndicadorActionPerformed

    private void rbEdadQuinquenal_IndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEdadQuinquenal_IndicadorActionPerformed
        this.rbSexo_Indicador.setSelected(false);
        this.rbTipoAfectado_Identificador.setSelected(false);
        this.rbTipoLesion_Indicador.setSelected(false);
    }//GEN-LAST:event_rbEdadQuinquenal_IndicadorActionPerformed

    private void rbTipoLesion_IndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTipoLesion_IndicadorActionPerformed
        this.rbSexo_Indicador.setSelected(false);
        this.rbTipoAfectado_Identificador.setSelected(false);
        this.rbEdadQuinquenal_Indicador.setSelected(false);
    }//GEN-LAST:event_rbTipoLesion_IndicadorActionPerformed

    private void rbTipoAfectado_IdentificadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTipoAfectado_IdentificadorActionPerformed
        this.rbSexo_Indicador.setSelected(false);
        this.rbTipoLesion_Indicador.setSelected(false);
        this.rbEdadQuinquenal_Indicador.setSelected(false);
    }//GEN-LAST:event_rbTipoAfectado_IdentificadorActionPerformed

    private void btnProcesarDashboard3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarDashboard3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnProcesarDashboard3ActionPerformed

    private void btnProcesarDashboard4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarDashboard4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnProcesarDashboard4ActionPerformed

    private void btnProcesarDashboard2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarDashboard2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnProcesarDashboard2ActionPerformed

    private void btnGenerarGrafica1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarGrafica1ActionPerformed
        //Set de rango anos
        String anos = (String)cbAñosConsulta2.getSelectedItem();
        if(anos.contains("-")){
            String[] anosSeparados = anos.split("-");
            dtoConsulta.setAño_ini(anosSeparados[0].replace(" ", ""));
            dtoConsulta.setAño_fin(anosSeparados[1].replace(" ", ""));
        }else{
            dtoConsulta.setAño_ini(anos);
            dtoConsulta.setAño_fin(anos);
        }
        
        //Set info de los indicadores
        ArrayList info = new ArrayList();
        if (rbSexo_Indicador.isSelected()){
            dtoConsulta.setTipoIdentificador(TipoIdentificador.SEXO);
            for(Object dato : listSexo_Grafica.getSelectedValues()){
                info.add((String)dato);
            }
        }
        
        if (rbEdadQuinquenal_Indicador.isSelected()){
            dtoConsulta.setTipoIdentificador(TipoIdentificador.EDAD_QUINQUENAL);
            for(Object dato : listEdadQuinquenal_Grafica.getSelectedValues()){
                info.add((String)dato);
            }
        }
        
        if (rbTipoAfectado_Identificador.isSelected()){
            dtoConsulta.setTipoIdentificador(TipoIdentificador.ROL_AFECTADO);
            for(Object dato : listRolAfectado_Grafica.getSelectedValues()){
                info.add((String)dato);
            }
        }
        if (rbTipoLesion_Indicador.isSelected()){
            dtoConsulta.setTipoIdentificador(TipoIdentificador.TIPO_LESION);
            for(Object dato : listTipoLesion_Grafica.getSelectedValues()){
                info.add((String)dato);
            }
        }
        dtoConsulta.setIndicadores(info);
        
        JFreeChart chart = controller.getGrafica(dtoConsulta).getGrafica();
        // Mostramos la grafica dentro del jPanel1
        ChartPanel panel = new ChartPanel(chart);        
        panelGrafico.setLayout(new java.awt.GridLayout());
        panelGrafico.add(panel);   
        panelGrafico.validate();        
    }//GEN-LAST:event_btnGenerarGrafica1ActionPerformed

    private void cbAñosConsulta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAñosConsulta2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAñosConsulta2ActionPerformed

    private void btnProcesarDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarDashboardActionPerformed
        TipoVista vista = TipoVista.PROVINCIA;
        boolean keepZoom = false;
        ArrayList<Marker> marcadores = new ArrayList<>();
        Marker.setCount(0);
        
        if (this.listCantones.getSelectedIndices().length > 0){
            vista = TipoVista.CANTON;
        }
        if (this.listDistritos.getSelectedIndices().length > 0){
            vista = TipoVista.DISTRITO;
        }
        
        if (vista == TipoVista.CANTON && this.listProvincias.getSelectedValue().equals("Puntarenas")){
            keepZoom = true;
        }
        
        
        marcadores.add(new Marker("10.023333333333333","-84.81083333333333",0));
        marcadores.add(new Marker("10.117222222222223","-84.82777777777777",0));
        marcadores.add(new Marker("9.170833333333333","-83.74583333333334",0));
        marcadores.add(new Marker("9.689444444444444","-85.10722222222222",0));
        marcadores.add(new Marker("8.627500000000001","-83.15611111111112",0));
        
        
        
        
        descargarMapa(vista,keepZoom,marcadores);
        
    }//GEN-LAST:event_btnProcesarDashboardActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainFrame().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPersonal;
    private javax.swing.JButton btnGenerarGrafica;
    private javax.swing.JButton btnGenerarGrafica1;
    private javax.swing.JButton btnProcesarDashboard;
    private javax.swing.JButton btnProcesarDashboard1;
    private javax.swing.JButton btnProcesarDashboard2;
    private javax.swing.JButton btnProcesarDashboard3;
    private javax.swing.JButton btnProcesarDashboard4;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JComboBox<String> cbAños;
    private javax.swing.JComboBox<String> cbAñosConsulta2;
    private javax.swing.JComboBox<String> cbEdadQuinquenal_Dashboard;
    private javax.swing.JComboBox<String> cbSexo_Dashboard;
    private javax.swing.JComboBox<String> cbTipoAfectado_Dashboard;
    private javax.swing.JComboBox<String> cbTipoLesion_Dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel labelMapa;
    private javax.swing.JList<String> listCantones;
    private javax.swing.JList<String> listDistritos;
    private javax.swing.JList<String> listEdadQuinquenal_Grafica;
    private javax.swing.JList<String> listProvincias;
    private javax.swing.JList<String> listRolAfectado_Grafica;
    private javax.swing.JList<String> listSexo_Grafica;
    private javax.swing.JList<String> listTipoLesion_Grafica;
    private javax.swing.JPanel panelAdicional;
    private javax.swing.JPanel panelBotones_Indicador;
    private javax.swing.JPanel panelDashboard;
    private javax.swing.JPanel panelGrafico;
    private javax.swing.JPanel panelIndicador;
    private javax.swing.JPanel panelLibre;
    private javax.swing.JTabbedPane panelPrincipal;
    private javax.swing.JRadioButton rbEdadQuinquenal_Indicador;
    private javax.swing.JRadioButton rbSexo_Indicador;
    private javax.swing.JRadioButton rbTipoAfectado_Identificador;
    private javax.swing.JRadioButton rbTipoLesion_Indicador;
    // End of variables declaration//GEN-END:variables
}
