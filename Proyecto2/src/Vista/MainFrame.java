/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.Controlador;
import Modelo.TipoVista;
import Modelo.Marker;
import Controlador.DTOConsulta;
import Controlador.DTOInterfaz;
import Modelo.TipoIdentificador;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author ayma-93
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     * @throws java.sql.SQLException
     */
    private Controlador controller;
    private DTOInterfaz dtoInterfaz_Entrada;
    private DTOInterfaz dtoInterfaz_Salida;
    private DTOConsulta dtoConsulta;
    
    //Definir modelo de datos para las listas de la interfaz
    private DefaultListModel modProvincia;
    private DefaultListModel modCanton;
    private DefaultListModel modDistrito;
    private DefaultListModel modTipoLesion;
    private DefaultListModel modRolAfectado;
    private DefaultListModel modEdadQuinquenal;
    
    
    public MainFrame() throws SQLException, ClassNotFoundException {
        initComponents();
        this.setLocation(0, 0); 
        this.rbSexo_Indicador.setSelected(true);
        this.controller = new Controlador();
        this.dtoInterfaz_Entrada = new DTOInterfaz();
        this.dtoInterfaz_Salida = new DTOInterfaz();
        this.dtoConsulta = new DTOConsulta();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < 30; i++) {
            series.add(i,i);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart("Generador de Gráficos",
                "Tiempo","Cantidad de Accidentes",dataset,
                PlotOrientation.VERTICAL,false,false,false);
        ChartPanel panel = new ChartPanel(chart);  
        panelGrafico.removeAll();
        panelGrafico.setLayout(new java.awt.GridLayout());
        panelGrafico.add(panel);   
        panelGrafico.validate();  
        listSexo_Grafica.setEnabled(true);
        listEdadQuinquenal_Grafica.setEnabled(false);
        listTipoLesion_Grafica.setEnabled(false);
        listRolAfectado_Grafica.setEnabled(false);
        
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("MAPA_PROVINCIAS.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
       Image dimg = img.getScaledInstance(this.lblMapaProvincias.getWidth(), this.lblMapaProvincias.getHeight(),
        Image.SCALE_SMOOTH);
       this.lblMapaProvincias.setIcon(new ImageIcon(dimg));
        
        descargarMapa(TipoVista.PROVINCIA,false,false,new ArrayList<>());
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
    
    private void descargarMapa(TipoVista vista,boolean keepZoomC,boolean keepZoomD,ArrayList<Marker> marcadores){
        
        this.dtoInterfaz_Salida.setVista(vista);
        this.dtoInterfaz_Salida.setKeepZoomC(keepZoomC);
        this.dtoInterfaz_Salida.setKeepZoomD(keepZoomD);
        this.dtoInterfaz_Salida.setMarcadores(marcadores);
        dtoInterfaz_Entrada = controller.getMapa(dtoInterfaz_Salida);
        try {
        URL url = new URL(dtoInterfaz_Entrada.getMapa().getLink());
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
        jLabel15 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        taLeyenda = new javax.swing.JTextArea();
        labelMapa = new javax.swing.JLabel();
        lblCargando = new javax.swing.JLabel();
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
        btnProcesarDashboard3 = new javax.swing.JButton();
        panelGrafico = new javax.swing.JPanel();
        panelLibre = new javax.swing.JPanel();
        btnProcesarDashboard4 = new javax.swing.JButton();
        lblMapaProvincias = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        taEstadisticasLibre = new javax.swing.JTextArea();
        lblAlajuela = new javax.swing.JLabel();
        lblCartago = new javax.swing.JLabel();
        lblHeredia = new javax.swing.JLabel();
        lblSanJose = new javax.swing.JLabel();
        lblGuanacaste = new javax.swing.JLabel();
        lblPuntarenas = new javax.swing.JLabel();
        lblLimon = new javax.swing.JLabel();
        cbAñosLibre = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
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
        listProvincias.setForeground(new java.awt.Color(255, 255, 255));
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

        cbAños.setBackground(new java.awt.Color(0, 153, 204));
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
        listCantones.setForeground(new java.awt.Color(255, 255, 255));
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
        listDistritos.setForeground(new java.awt.Color(255, 255, 255));
        listDistritos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listDistritos);

        cbSexo_Dashboard.setBackground(new java.awt.Color(0, 153, 204));
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

        cbTipoAfectado_Dashboard.setBackground(new java.awt.Color(0, 153, 204));
        cbTipoAfectado_Dashboard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Ciclista", "Conductor", "Dueño de Propiedad", "Motociclista", "Pasajero Bicicleta", "Pasajero Bus o MicroBus", "Pasajero Carro", "Pasajero Moto", "Peaton", "Otro" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tipo de Lesión");

        cbTipoLesion_Dashboard.setBackground(new java.awt.Color(0, 153, 204));
        cbTipoLesion_Dashboard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Ileso", "Herido Leve ", "Herido Grave", "Muerte" }));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Edad Quinquenal");

        cbEdadQuinquenal_Dashboard.setBackground(new java.awt.Color(0, 153, 204));
        cbEdadQuinquenal_Dashboard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "De 0 a 4 años", "De 5 a 9 años", "De 10 a 14 años", "De 15 a 19 años", "De 20 a 24 años", "De 25 a 29 años", "De 30 a 34 años", "De 35 a 39 años", "De 40 a 44 años", "De 45 a 49 años", "De 50 a 54 años", "De 55 a 59 años", "De 60 a 64 años", "De 65 a 69 años", "De 70 a 74 años", "Mayor a 75 años", "Desconocida" }));

        btnProcesarDashboard1.setBackground(new java.awt.Color(0, 153, 204));
        btnProcesarDashboard1.setLabel("LIMPIAR CONSULTA");
        btnProcesarDashboard1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarDashboard1ActionPerformed(evt);
            }
        });

        btnProcesarDashboard.setBackground(new java.awt.Color(0, 153, 204));
        btnProcesarDashboard.setText("GENERAR CONSULTA");
        btnProcesarDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProcesarDashboardMouseClicked(evt);
            }
        });
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

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("LEYENDA");

        taLeyenda.setEditable(false);
        taLeyenda.setBackground(new java.awt.Color(102, 102, 102));
        taLeyenda.setColumns(20);
        taLeyenda.setForeground(new java.awt.Color(255, 255, 255));
        taLeyenda.setRows(5);
        jScrollPane8.setViewportView(taLeyenda);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(cbTipoAfectado_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(cbEdadQuinquenal_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(cbTipoLesion_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cbAños, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbSexo_Dashboard, javax.swing.GroupLayout.Alignment.LEADING, 0, 126, Short.MAX_VALUE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnProcesarDashboard2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnProcesarDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnProcesarDashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(22, 22, 22)
                        .addComponent(cbAños, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbSexo_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTipoAfectado_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTipoLesion_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbEdadQuinquenal_Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnProcesarDashboard)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProcesarDashboard1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProcesarDashboard2)))
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblCargando.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelAdicionalLayout = new javax.swing.GroupLayout(panelAdicional);
        panelAdicional.setLayout(panelAdicionalLayout);
        panelAdicionalLayout.setHorizontalGroup(
            panelAdicionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdicionalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAdicionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(panelAdicionalLayout.createSequentialGroup()
                        .addGroup(panelAdicionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 927, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCargando))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelAdicionalLayout.setVerticalGroup(
            panelAdicionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdicionalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAdicionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdicionalLayout.createSequentialGroup()
                        .addComponent(labelMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCargando)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

        cbAñosConsulta2.setBackground(new java.awt.Color(0, 153, 204));
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

        btnGenerarGrafica1.setBackground(new java.awt.Color(0, 153, 204));
        btnGenerarGrafica1.setText("GENERAR GRÁFICA");
        btnGenerarGrafica1.setActionCommand("");
        btnGenerarGrafica1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarGrafica1ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Seleccione el rango de fechas:");

        listRolAfectado_Grafica.setBackground(new java.awt.Color(102, 102, 102));
        listRolAfectado_Grafica.setForeground(new java.awt.Color(255, 255, 255));
        listRolAfectado_Grafica.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(listRolAfectado_Grafica);

        listSexo_Grafica.setBackground(new java.awt.Color(102, 102, 102));
        listSexo_Grafica.setForeground(new java.awt.Color(255, 255, 255));
        listSexo_Grafica.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Hombre", "Mujer", "Desconocido" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(listSexo_Grafica);

        listTipoLesion_Grafica.setBackground(new java.awt.Color(102, 102, 102));
        listTipoLesion_Grafica.setForeground(new java.awt.Color(255, 255, 255));
        listTipoLesion_Grafica.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(listTipoLesion_Grafica);

        listEdadQuinquenal_Grafica.setBackground(new java.awt.Color(102, 102, 102));
        listEdadQuinquenal_Grafica.setForeground(new java.awt.Color(255, 255, 255));
        listEdadQuinquenal_Grafica.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(listEdadQuinquenal_Grafica);

        btnProcesarDashboard3.setBackground(new java.awt.Color(0, 153, 204));
        btnProcesarDashboard3.setText("CERRAR SISTEMA");
        btnProcesarDashboard3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarDashboard3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotones_IndicadorLayout = new javax.swing.GroupLayout(panelBotones_Indicador);
        panelBotones_Indicador.setLayout(panelBotones_IndicadorLayout);
        panelBotones_IndicadorLayout.setHorizontalGroup(
            panelBotones_IndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotones_IndicadorLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelBotones_IndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbSexo_Indicador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbTipoLesion_Indicador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rbTipoAfectado_Identificador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addComponent(rbEdadQuinquenal_Indicador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane7)
                    .addGroup(panelBotones_IndicadorLayout.createSequentialGroup()
                        .addGroup(panelBotones_IndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnProcesarDashboard3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGenerarGrafica1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGenerarGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbAñosConsulta2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelBotones_IndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
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
            .addGap(0, 609, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelIndicadorLayout = new javax.swing.GroupLayout(panelIndicador);
        panelIndicador.setLayout(panelIndicadorLayout);
        panelIndicadorLayout.setHorizontalGroup(
            panelIndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIndicadorLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBotones_Indicador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelIndicadorLayout.setVerticalGroup(
            panelIndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIndicadorLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panelIndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBotones_Indicador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelIndicadorLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(485, Short.MAX_VALUE))
        );

        panelPrincipal.addTab("Indicador de Comportamiento", panelIndicador);

        panelLibre.setBackground(new java.awt.Color(51, 51, 51));
        panelLibre.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panelLibreMouseMoved(evt);
            }
        });

        btnProcesarDashboard4.setBackground(new java.awt.Color(0, 153, 204));
        btnProcesarDashboard4.setText("CERRAR SISTEMA");
        btnProcesarDashboard4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarDashboard4ActionPerformed(evt);
            }
        });

        lblMapaProvincias.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblMapaProvinciasMouseMoved(evt);
            }
        });

        taEstadisticasLibre.setEditable(false);
        taEstadisticasLibre.setBackground(new java.awt.Color(102, 102, 102));
        taEstadisticasLibre.setColumns(20);
        taEstadisticasLibre.setFont(new java.awt.Font("Monospaced", 1, 22)); // NOI18N
        taEstadisticasLibre.setForeground(new java.awt.Color(255, 255, 255));
        taEstadisticasLibre.setLineWrap(true);
        taEstadisticasLibre.setRows(5);
        jScrollPane9.setViewportView(taEstadisticasLibre);

        lblAlajuela.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblAlajuela.setForeground(new java.awt.Color(255, 255, 255));
        lblAlajuela.setText("ALAJUELA");
        lblAlajuela.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblAlajuelaMouseMoved(evt);
            }
        });

        lblCartago.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCartago.setForeground(new java.awt.Color(255, 255, 255));
        lblCartago.setText("CARTAGO");
        lblCartago.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblCartagoMouseMoved(evt);
            }
        });

        lblHeredia.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHeredia.setForeground(new java.awt.Color(255, 255, 255));
        lblHeredia.setText("HEREDIA");
        lblHeredia.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblHerediaMouseMoved(evt);
            }
        });

        lblSanJose.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSanJose.setForeground(new java.awt.Color(255, 255, 255));
        lblSanJose.setText("SAN JOSE");
        lblSanJose.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblSanJoseMouseMoved(evt);
            }
        });

        lblGuanacaste.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblGuanacaste.setForeground(new java.awt.Color(255, 255, 255));
        lblGuanacaste.setText("GUANACASTE");
        lblGuanacaste.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblGuanacasteMouseMoved(evt);
            }
        });

        lblPuntarenas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPuntarenas.setForeground(new java.awt.Color(255, 255, 255));
        lblPuntarenas.setText("PUNTARENAS");
        lblPuntarenas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblPuntarenasMouseMoved(evt);
            }
        });

        lblLimon.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblLimon.setForeground(new java.awt.Color(255, 255, 255));
        lblLimon.setText("LIMON");
        lblLimon.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblLimonMouseMoved(evt);
            }
        });

        cbAñosLibre.setBackground(new java.awt.Color(0, 153, 204));
        cbAñosLibre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2012", "2013", "2014", "2012 - 2013", "2012 - 2014", "2013 - 2014" }));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Año(s)");

        javax.swing.GroupLayout panelLibreLayout = new javax.swing.GroupLayout(panelLibre);
        panelLibre.setLayout(panelLibreLayout);
        panelLibreLayout.setHorizontalGroup(
            panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLibreLayout.createSequentialGroup()
                .addGroup(panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLibreLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblAlajuela)
                        .addGap(18, 18, 18)
                        .addComponent(lblCartago)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHeredia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSanJose)
                        .addGap(18, 18, 18)
                        .addComponent(lblGuanacaste)
                        .addGap(30, 30, 30)
                        .addComponent(lblLimon)
                        .addGap(18, 18, 18)
                        .addComponent(lblPuntarenas))
                    .addComponent(lblMapaProvincias, javax.swing.GroupLayout.PREFERRED_SIZE, 1012, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLibreLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(btnProcesarDashboard4, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(panelLibreLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLibreLayout.createSequentialGroup()
                                .addGroup(panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbAñosLibre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))
                                .addContainerGap())))))
        );

        panelLibreLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblAlajuela, lblCartago, lblGuanacaste, lblHeredia, lblLimon, lblPuntarenas, lblSanJose});

        panelLibreLayout.setVerticalGroup(
            panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLibreLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelLibreLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(cbAñosLibre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane9))
                    .addComponent(lblMapaProvincias, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelLibreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAlajuela)
                    .addComponent(lblCartago)
                    .addComponent(lblHeredia)
                    .addComponent(lblSanJose)
                    .addComponent(lblGuanacaste)
                    .addComponent(lblPuntarenas)
                    .addComponent(lblLimon)
                    .addComponent(btnProcesarDashboard4))
                .addContainerGap(500, Short.MAX_VALUE))
        );

        panelLibreLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblAlajuela, lblCartago, lblGuanacaste, lblHeredia, lblLimon, lblPuntarenas, lblSanJose});

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
            .addGroup(jPanel2Layout.createSequentialGroup()
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        XYSeries series = new XYSeries("");
        for (int i = 0; i < 30; i++) {
            series.add(i,i);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart("Generador de Gráficos",
                "Tiempo","Cantidad de Accidentes",dataset,
                PlotOrientation.VERTICAL,false,false,false);
        ChartPanel panel = new ChartPanel(chart);  
        panelGrafico.removeAll();
        panelGrafico.setLayout(new java.awt.GridLayout());
        panelGrafico.add(panel);   
        panelGrafico.validate();
        this.rbSexo_Indicador.setSelected(true);
        this.rbEdadQuinquenal_Indicador.setSelected(false);
        this.rbTipoLesion_Indicador.setSelected(false);
        this.rbTipoAfectado_Identificador.setSelected(false);
        listEdadQuinquenal_Grafica.setEnabled(false);
        listRolAfectado_Grafica.setEnabled(false);
        listTipoLesion_Grafica.setEnabled(false);
        cbAñosConsulta2.setSelectedIndex(0);
        
    }//GEN-LAST:event_btnGenerarGraficaActionPerformed

    private void rbSexo_IndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSexo_IndicadorActionPerformed
        this.rbEdadQuinquenal_Indicador.setSelected(false);
        this.rbTipoAfectado_Identificador.setSelected(false);
        this.rbTipoLesion_Indicador.setSelected(false);
        listEdadQuinquenal_Grafica.setEnabled(false);
        listRolAfectado_Grafica.setEnabled(false);
        listTipoLesion_Grafica.setEnabled(false);
        listSexo_Grafica.setEnabled(true);
    }//GEN-LAST:event_rbSexo_IndicadorActionPerformed

    private void rbEdadQuinquenal_IndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEdadQuinquenal_IndicadorActionPerformed
        this.rbSexo_Indicador.setSelected(false);
        this.rbTipoAfectado_Identificador.setSelected(false);
        this.rbTipoLesion_Indicador.setSelected(false);
        listSexo_Grafica.setEnabled(false);
        listRolAfectado_Grafica.setEnabled(false);
        listTipoLesion_Grafica.setEnabled(false);
        listEdadQuinquenal_Grafica.setEnabled(true);
    }//GEN-LAST:event_rbEdadQuinquenal_IndicadorActionPerformed

    private void rbTipoLesion_IndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTipoLesion_IndicadorActionPerformed
        this.rbSexo_Indicador.setSelected(false);
        this.rbTipoAfectado_Identificador.setSelected(false);
        this.rbEdadQuinquenal_Indicador.setSelected(false);
        listSexo_Grafica.setEnabled(false);
        listRolAfectado_Grafica.setEnabled(false);
        listEdadQuinquenal_Grafica.setEnabled(false);
        listTipoLesion_Grafica.setEnabled(true);
    }//GEN-LAST:event_rbTipoLesion_IndicadorActionPerformed

    private void rbTipoAfectado_IdentificadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTipoAfectado_IdentificadorActionPerformed
        this.rbSexo_Indicador.setSelected(false);
        this.rbTipoLesion_Indicador.setSelected(false);
        this.rbEdadQuinquenal_Indicador.setSelected(false);
        listSexo_Grafica.setEnabled(false);
        listTipoLesion_Grafica.setEnabled(false);
        listEdadQuinquenal_Grafica.setEnabled(false);
        listRolAfectado_Grafica.setEnabled(true);
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
        
        dtoInterfaz_Entrada = controller.getDao().getEdadesQuinquenal(dtoInterfaz_Salida);
        ResultSet rs = dtoInterfaz_Entrada.getRs();
        
        //Set info de los indicadores
        ArrayList info = new ArrayList();
        if (rbSexo_Indicador.isSelected()){
            dtoConsulta.setTipoIdentificador(TipoIdentificador.SEXO);
            if (listSexo_Grafica.isSelectionEmpty()){
                info.add("Hombre");
                info.add("Mujer");
                info.add("Desconocido");
            }else{
                for(Object dato : listSexo_Grafica.getSelectedValues()){
                    info.add((String)dato);
                }
            }
        }
        
        if (rbEdadQuinquenal_Indicador.isSelected()){
            dtoConsulta.setTipoIdentificador(TipoIdentificador.EDAD_QUINQUENAL);
            if (listEdadQuinquenal_Grafica.isSelectionEmpty()){
                try {
                    while(rs.next()){
                        String dato = rs.getString("EdadQuinquenal");
                        info.add(dato);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                for(Object dato : listEdadQuinquenal_Grafica.getSelectedValues()){
                    info.add((String)dato);
                }
            }
            
        }
        dtoInterfaz_Entrada = controller.getDao().getTiposLesion(dtoInterfaz_Salida);
        rs = dtoInterfaz_Entrada.getRs();
        
        if (rbTipoLesion_Indicador.isSelected()){
            dtoConsulta.setTipoIdentificador(TipoIdentificador.TIPO_LESION);
            if (listTipoLesion_Grafica.isSelectionEmpty()){
                try {
                    while(rs.next()){
                        String dato = rs.getString("TipoLesion");
                        info.add(dato);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                for(Object dato : listTipoLesion_Grafica.getSelectedValues()){
                    info.add((String)dato);
                }
            }
        }
        
        dtoInterfaz_Entrada = controller.getDao().getRolesAfectado(dtoInterfaz_Salida);
        rs = dtoInterfaz_Entrada.getRs();
        
        if (rbTipoAfectado_Identificador.isSelected()){
            dtoConsulta.setTipoIdentificador(TipoIdentificador.ROL_AFECTADO);
            if (listRolAfectado_Grafica.isSelectionEmpty()){
                try {
                    while(rs.next()){
                        String dato = rs.getString("RolAfectado");
                        info.add(dato);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                for(Object dato : listRolAfectado_Grafica.getSelectedValues()){
                    info.add((String)dato);
                }
            }
        }
        dtoConsulta.setIndicadores(info);
        
        JFreeChart chart = controller.getGrafica(dtoConsulta).getGrafica();
        // Mostramos la grafica dentro del jPanel1
        ChartPanel panel = new ChartPanel(chart);  
        panelGrafico.removeAll();
        panelGrafico.setLayout(new java.awt.GridLayout());
        panelGrafico.add(panel);   
        panelGrafico.validate();
    }//GEN-LAST:event_btnGenerarGrafica1ActionPerformed

    private void cbAñosConsulta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAñosConsulta2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAñosConsulta2ActionPerformed

    private void btnProcesarDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarDashboardActionPerformed
        this.dtoConsulta = new DTOConsulta();
        this.taLeyenda.setText("");
        TipoVista vista = TipoVista.PROVINCIA;
        boolean keepZoomC = false;
        boolean keepZoomD = false;
        ArrayList<Marker> marcadores = new ArrayList<>();
        Marker.setCount(0);
        if (this.listCantones.getSelectedIndices().length > 0){
            vista = TipoVista.CANTON;
        }
        if (this.listDistritos.getSelectedIndices().length > 0){
            vista = TipoVista.DISTRITO;
        }
        
        if (vista == TipoVista.CANTON && this.listProvincias.getSelectedValue().equals("Puntarenas")){
            keepZoomC = true;
        }
        
        if (vista == TipoVista.DISTRITO && this.listProvincias.getSelectedValue().equals("Puntarenas")){
            keepZoomD = true;
        }
        
        
        //Generador de consulta
        String anos = (String)cbAños.getSelectedItem();
        if(anos.contains("-")){
            String[] anosSeparados = anos.split("-");
            dtoConsulta.setAño_ini(anosSeparados[0].replace(" ", ""));
            dtoConsulta.setAño_fin(anosSeparados[1].replace(" ", ""));
        }else{
            dtoConsulta.setAño_ini(anos);
            dtoConsulta.setAño_fin(anos);
        }
        
        
        
        
        String sexo = null;
        if (!(this.cbSexo_Dashboard.getSelectedItem().toString().equals("-"))){
            sexo = this.cbSexo_Dashboard.getSelectedItem().toString();
        }
        dtoConsulta.setSexo(sexo);
        
        
        
        String tipo_lesion = null;
        if (!(this.cbTipoLesion_Dashboard.getSelectedItem().toString().equals("-"))){
            tipo_lesion = this.cbTipoLesion_Dashboard.getSelectedItem().toString();
        }
        dtoConsulta.setTipo_lesion(tipo_lesion);
        
        
        String rol_afectado = null;
        if (!(this.cbTipoAfectado_Dashboard.getSelectedItem().toString().equals("-"))){
            rol_afectado = this.cbTipoAfectado_Dashboard.getSelectedItem().toString();
        }
        dtoConsulta.setRol_afectado(rol_afectado);
        
        String edad_quinquenial = null;
        if (!(this.cbEdadQuinquenal_Dashboard.getSelectedItem().toString().equals("-"))){
            edad_quinquenial = this.cbEdadQuinquenal_Dashboard.getSelectedItem().toString();
        }
        dtoConsulta.setEdad_quinquenal(edad_quinquenial);
        
        
        
        // hacer por cada consulta
        if (this.listProvincias.getSelectedValuesList().size() == 0){
            dtoConsulta = controller.consultaDashboard(dtoConsulta);
                ResultSet rs = dtoConsulta.getRs();
                int cantidad = 0;

                try {
                    while(rs.next()){
                        cantidad = Integer.valueOf(rs.getString("Cantidad"));
                        marcadores.add(new Marker("9.93638888888889000000","-84.07444444444440000000",cantidad,"Costa Rica"));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
        }
        
        
        if (vista == TipoVista.PROVINCIA){
            for (String p:this.listProvincias.getSelectedValuesList()){
                dtoConsulta.setProvincia(p);
                dtoConsulta = controller.consultaDashboard(dtoConsulta);
                ResultSet rs = dtoConsulta.getRs();


                try {
                    while(rs.next()){
                        String lat = rs.getString("Latitud");
                        String lon = rs.getString("Longitud");
                        int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                        marcadores.add(new Marker(lat,lon,cantidad,p));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
        if (vista == TipoVista.CANTON){
            dtoConsulta.setProvincia(this.listProvincias.getSelectedValue());
            for (String p:this.listCantones.getSelectedValuesList()){
                dtoConsulta.setCanton(p);
                dtoConsulta = controller.consultaDashboard(dtoConsulta);
                ResultSet rs = dtoConsulta.getRs();


                try {
                    while(rs.next()){
                        String lat = rs.getString("Latitud");
                        String lon = rs.getString("Longitud");
                        int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                        marcadores.add(new Marker(lat,lon,cantidad,p));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
        if (vista == TipoVista.DISTRITO){
            dtoConsulta.setProvincia(this.listProvincias.getSelectedValue());
            dtoConsulta.setCanton(this.listCantones.getSelectedValue());
            for (String p:this.listDistritos.getSelectedValuesList()){
                dtoConsulta.setDistrito(p);
                dtoConsulta = controller.consultaDashboard(dtoConsulta);
                ResultSet rs = dtoConsulta.getRs();


                try {
                    while(rs.next()){
                        String lat = rs.getString("Latitud");
                        String lon = rs.getString("Longitud");
                        int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                        marcadores.add(new Marker(lat,lon,cantidad,p));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        int sumatotal = 0;
        for (Marker m:marcadores){
            sumatotal += m.getCantidad();
            this.taLeyenda.setText(this.taLeyenda.getText()+m.getLabel()+" - "+m.getNombre()+" - "+m.getCantidad()+"\n");
        }
        this.taLeyenda.setText(this.taLeyenda.getText()+"\n\nTotal: "+sumatotal);
        
        //Final de consulta
       
        
        
        
        
        descargarMapa(vista,keepZoomC,keepZoomD,marcadores);
        
        this.lblCargando.setText("Cargado!");
    }//GEN-LAST:event_btnProcesarDashboardActionPerformed

    private void btnProcesarDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProcesarDashboardMouseClicked
        
    }//GEN-LAST:event_btnProcesarDashboardMouseClicked

    private void btnProcesarDashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarDashboard1ActionPerformed
        descargarMapa(TipoVista.PROVINCIA,false,false,new ArrayList<>());
        this.listDistritos.clearSelection();
        this.listCantones.clearSelection();
        this.listProvincias.clearSelection();
        this.cbAños.setSelectedIndex(0);
        this.cbEdadQuinquenal_Dashboard.setSelectedIndex(0);
        this.cbSexo_Dashboard.setSelectedIndex(0);
        this.cbTipoAfectado_Dashboard.setSelectedIndex(0);
        this.cbTipoLesion_Dashboard.setSelectedIndex(0);
        this.taLeyenda.setText("");
        this.lblCargando.setText("Limpio !");
    }//GEN-LAST:event_btnProcesarDashboard1ActionPerformed

    private void lblAlajuelaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAlajuelaMouseMoved
        String provincia = "Alajuela";
        this.taEstadisticasLibre.setText("");
        String anos = (String)cbAñosLibre.getSelectedItem();
        if(anos.contains("-")){
            String[] anosSeparados = anos.split("-");
            dtoConsulta.setAño_ini(anosSeparados[0].replace(" ", ""));
            dtoConsulta.setAño_fin(anosSeparados[1].replace(" ", ""));
        }else{
            dtoConsulta.setAño_ini(anos);
            dtoConsulta.setAño_fin(anos);
        }
        dtoConsulta.setProvincia(provincia);
        
        dtoConsulta = controller.consultaLibre(dtoConsulta);
        int sumatotal = 0;
        ResultSet rs = dtoConsulta.getRs();
        this.taEstadisticasLibre.setText("Estadisticas de\n"+provincia+"\npara "+dtoConsulta.getAño_ini()+"-"+dtoConsulta.getAño_fin()+"\n\n");
        try {
            while(rs.next()){
                String desc = rs.getString("Lesion");
                int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                sumatotal += cantidad;
                this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+desc+"\t"+cantidad+"\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+"\n\nTotal\t"+sumatotal);
    }//GEN-LAST:event_lblAlajuelaMouseMoved

    private void panelLibreMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLibreMouseMoved
        this.taEstadisticasLibre.setText("");
    }//GEN-LAST:event_panelLibreMouseMoved

    private void lblMapaProvinciasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMapaProvinciasMouseMoved
        this.taEstadisticasLibre.setText("Coloquese encima de\nuna de las ETIQUETAS\ncon los nombres de\nprovincias para ver \nlas estadisticas\ncorrespondientes por\nTipo de lesion");
    }//GEN-LAST:event_lblMapaProvinciasMouseMoved

    private void lblCartagoMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCartagoMouseMoved
        this.taEstadisticasLibre.setText("");
        String provincia = "Cartago";
        String anos = (String)cbAñosLibre.getSelectedItem();
        if(anos.contains("-")){
            String[] anosSeparados = anos.split("-");
            dtoConsulta.setAño_ini(anosSeparados[0].replace(" ", ""));
            dtoConsulta.setAño_fin(anosSeparados[1].replace(" ", ""));
        }else{
            dtoConsulta.setAño_ini(anos);
            dtoConsulta.setAño_fin(anos);
        }
        dtoConsulta.setProvincia(provincia);
        
        dtoConsulta = controller.consultaLibre(dtoConsulta);
        int sumatotal = 0;
        ResultSet rs = dtoConsulta.getRs();
        this.taEstadisticasLibre.setText("Estadisticas de\n"+provincia+"\npara "+dtoConsulta.getAño_ini()+"-"+dtoConsulta.getAño_fin()+"\n\n");
        try {
            while(rs.next()){
                String desc = rs.getString("Lesion");
                int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                sumatotal += cantidad;
                this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+desc+"\t"+cantidad+"\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+"\n\nTotal\t"+sumatotal);
    }//GEN-LAST:event_lblCartagoMouseMoved

    private void lblHerediaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHerediaMouseMoved
        this.taEstadisticasLibre.setText("");
        String provincia = "Heredia";
        String anos = (String)cbAñosLibre.getSelectedItem();
        if(anos.contains("-")){
            String[] anosSeparados = anos.split("-");
            dtoConsulta.setAño_ini(anosSeparados[0].replace(" ", ""));
            dtoConsulta.setAño_fin(anosSeparados[1].replace(" ", ""));
        }else{
            dtoConsulta.setAño_ini(anos);
            dtoConsulta.setAño_fin(anos);
        }
        dtoConsulta.setProvincia(provincia);
        
        dtoConsulta = controller.consultaLibre(dtoConsulta);
        int sumatotal = 0;
        ResultSet rs = dtoConsulta.getRs();
        this.taEstadisticasLibre.setText("Estadisticas de\n"+provincia+"\npara "+dtoConsulta.getAño_ini()+"-"+dtoConsulta.getAño_fin()+"\n\n");
        try {
            while(rs.next()){
                String desc = rs.getString("Lesion");
                int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                sumatotal += cantidad;
                this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+desc+"\t"+cantidad+"\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+"\n\nTotal\t"+sumatotal);
    }//GEN-LAST:event_lblHerediaMouseMoved

    private void lblSanJoseMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanJoseMouseMoved
        String provincia = "San José";
        this.taEstadisticasLibre.setText("");
        String anos = (String)cbAñosLibre.getSelectedItem();
        if(anos.contains("-")){
            String[] anosSeparados = anos.split("-");
            dtoConsulta.setAño_ini(anosSeparados[0].replace(" ", ""));
            dtoConsulta.setAño_fin(anosSeparados[1].replace(" ", ""));
        }else{
            dtoConsulta.setAño_ini(anos);
            dtoConsulta.setAño_fin(anos);
        }
        dtoConsulta.setProvincia(provincia);
        
        dtoConsulta = controller.consultaLibre(dtoConsulta);
        int sumatotal = 0;
        ResultSet rs = dtoConsulta.getRs();
        this.taEstadisticasLibre.setText("Estadisticas de\n"+provincia+"\npara "+dtoConsulta.getAño_ini()+"-"+dtoConsulta.getAño_fin()+"\n\n");
        try {
            while(rs.next()){
                String desc = rs.getString("Lesion");
                int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                sumatotal += cantidad;
                this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+desc+"\t"+cantidad+"\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+"\n\nTotal\t"+sumatotal);
    }//GEN-LAST:event_lblSanJoseMouseMoved

    private void lblGuanacasteMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGuanacasteMouseMoved
        String provincia = "Guanacaste";
        this.taEstadisticasLibre.setText("");
        String anos = (String)cbAñosLibre.getSelectedItem();
        if(anos.contains("-")){
            String[] anosSeparados = anos.split("-");
            dtoConsulta.setAño_ini(anosSeparados[0].replace(" ", ""));
            dtoConsulta.setAño_fin(anosSeparados[1].replace(" ", ""));
        }else{
            dtoConsulta.setAño_ini(anos);
            dtoConsulta.setAño_fin(anos);
        }
        dtoConsulta.setProvincia(provincia);
        
        dtoConsulta = controller.consultaLibre(dtoConsulta);
        int sumatotal = 0;
        ResultSet rs = dtoConsulta.getRs();
        this.taEstadisticasLibre.setText("Estadisticas de\n"+provincia+"\npara "+dtoConsulta.getAño_ini()+"-"+dtoConsulta.getAño_fin()+"\n\n");
        try {
            while(rs.next()){
                String desc = rs.getString("Lesion");
                int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                sumatotal += cantidad;
                this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+desc+"\t"+cantidad+"\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+"\n\nTotal\t"+sumatotal);
    }//GEN-LAST:event_lblGuanacasteMouseMoved

    private void lblLimonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimonMouseMoved
        String provincia = "Limón";
        this.taEstadisticasLibre.setText("");
        String anos = (String)cbAñosLibre.getSelectedItem();
        if(anos.contains("-")){
            String[] anosSeparados = anos.split("-");
            dtoConsulta.setAño_ini(anosSeparados[0].replace(" ", ""));
            dtoConsulta.setAño_fin(anosSeparados[1].replace(" ", ""));
        }else{
            dtoConsulta.setAño_ini(anos);
            dtoConsulta.setAño_fin(anos);
        }
        dtoConsulta.setProvincia(provincia);
        
        dtoConsulta = controller.consultaLibre(dtoConsulta);
        int sumatotal = 0;
        ResultSet rs = dtoConsulta.getRs();
        this.taEstadisticasLibre.setText("Estadisticas de\n"+provincia+"\npara "+dtoConsulta.getAño_ini()+"-"+dtoConsulta.getAño_fin()+"\n\n");
        try {
            while(rs.next()){
                String desc = rs.getString("Lesion");
                int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                sumatotal += cantidad;
                this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+desc+"\t"+cantidad+"\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+"\n\nTotal\t"+sumatotal);
    }//GEN-LAST:event_lblLimonMouseMoved

    private void lblPuntarenasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPuntarenasMouseMoved
        String provincia = "Puntarenas";
        this.taEstadisticasLibre.setText("");
        String anos = (String)cbAñosLibre.getSelectedItem();
        if(anos.contains("-")){
            String[] anosSeparados = anos.split("-");
            dtoConsulta.setAño_ini(anosSeparados[0].replace(" ", ""));
            dtoConsulta.setAño_fin(anosSeparados[1].replace(" ", ""));
        }else{
            dtoConsulta.setAño_ini(anos);
            dtoConsulta.setAño_fin(anos);
        }
        dtoConsulta.setProvincia(provincia);
        
        
        dtoConsulta = controller.consultaLibre(dtoConsulta);
        int sumatotal = 0;
        ResultSet rs = dtoConsulta.getRs();
        this.taEstadisticasLibre.setText("Estadisticas de\n"+provincia+"\npara "+dtoConsulta.getAño_ini()+"-"+dtoConsulta.getAño_fin()+"\n\n");
        try {
            while(rs.next()){
                String desc = rs.getString("Lesion");
                int cantidad = Integer.valueOf(rs.getString("Cantidad"));
                sumatotal += cantidad;
                this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+desc+"\t"+cantidad+"\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.taEstadisticasLibre.setText(this.taEstadisticasLibre.getText()+"\n\nTotal\t"+sumatotal);
    }//GEN-LAST:event_lblPuntarenasMouseMoved

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
    private javax.swing.JComboBox<String> cbAñosLibre;
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
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel labelMapa;
    private javax.swing.JLabel lblAlajuela;
    private javax.swing.JLabel lblCargando;
    private javax.swing.JLabel lblCartago;
    private javax.swing.JLabel lblGuanacaste;
    private javax.swing.JLabel lblHeredia;
    private javax.swing.JLabel lblLimon;
    private javax.swing.JLabel lblMapaProvincias;
    private javax.swing.JLabel lblPuntarenas;
    private javax.swing.JLabel lblSanJose;
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
    private javax.swing.JTextArea taEstadisticasLibre;
    private javax.swing.JTextArea taLeyenda;
    // End of variables declaration//GEN-END:variables
}
