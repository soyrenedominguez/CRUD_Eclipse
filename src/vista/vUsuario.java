package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.daoUsuario;
import modelo.Usuario;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class vUsuario extends JFrame {

	private JPanel contentPane;
	private JLabel lblId;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnActualizar;
	private JButton btnLimpiar;
	private JTextField txtNombre;
	private JTextField txtPassword;
	private JTextField txtUser;
	daoUsuario dao = new daoUsuario();
	DefaultTableModel modelo = new DefaultTableModel();
	ArrayList<Usuario> lista;
	private JTable tblUsuarios;
	int fila = -1;
	Usuario usuario = new Usuario();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vUsuario frame = new vUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void actualizarTabla() {

		while (modelo.getRowCount() > 0) {
			modelo.removeRow(0);
		}
		lista = dao.consultaUsuarios();
		for (Usuario u : lista) {
			Object user[] = new Object[4];
			user[0] = u.getId();
			user[1] = u.getUser();
			user[2] = u.getPassword();
			user[3] = u.getNombre();
			modelo.addRow(user);
		}
		tblUsuarios.setModel(modelo);
	}

	public void limpiar() {
		lblId.setText("");
		txtUser.setText("");
		txtPassword.setText("");
		txtNombre.setText("");
	}

	public vUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 884, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icono.png")));
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("CRUD USUARIOS");
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(26, 31, 87, 31);
		contentPane.add(lblNewLabel);

		lblId = new JLabel("0");
		lblId.setFont(new Font("Arial", Font.BOLD, 14));
		lblId.setBounds(123, 31, 87, 31);
		contentPane.add(lblId);

		JLabel lblUser = new JLabel("USER:");
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser.setFont(new Font("Arial", Font.BOLD, 14));
		lblUser.setBounds(26, 73, 87, 31);
		contentPane.add(lblUser);

		JLabel lblPassword = new JLabel("PASSWORD:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
		lblPassword.setBounds(26, 112, 87, 31);
		contentPane.add(lblPassword);

		JLabel lblNombre = new JLabel("NOMBRE:");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
		lblNombre.setBounds(26, 154, 87, 31);
		contentPane.add(lblNombre);

		txtUser = new JTextField();
		txtUser.setBounds(123, 73, 176, 20);
		contentPane.add(txtUser);
		txtUser.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(123, 118, 176, 20);
		contentPane.add(txtPassword);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(123, 160, 176, 20);
		contentPane.add(txtNombre);

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtUser.getText().equals("") || txtPassword.getText().equals("")
							|| txtNombre.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "CAMPOS VACIOS");
						return;
					}
					Usuario user = new Usuario();
					user.setUser(txtUser.getText());
					user.setPassword(txtPassword.getText());
					user.setNombre(txtNombre.getText());
					if (dao.insertarUsuario(user)) {
						actualizarTabla();
						limpiar();
						JOptionPane.showMessageDialog(null, "SE AGREGO CORRECTAMENTE!!");
					} else {
						JOptionPane.showMessageDialog(null, "ERROR");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "ERROR");
				}
			}
		});
		btnAgregar.setBounds(121, 212, 89, 23);
		contentPane.add(btnAgregar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int opcion = JOptionPane.showConfirmDialog(null, "Estás seguro de eliminar este Usuario?",
							"ELIMINAR USUARIO", JOptionPane.YES_NO_OPTION);
					if (opcion == 0) {
						if (dao.eliminarUsuario(usuario.getId())&&usuario.getId()>0) {
							actualizarTabla();
							limpiar();
							JOptionPane.showMessageDialog(null, "SE ELIMINO CORRECTAMENTE!!");
						} else {
							JOptionPane.showMessageDialog(null, "ERROR");
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR");
				}
			}
		});
		btnEliminar.setBounds(123, 246, 89, 23);
		contentPane.add(btnEliminar);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtUser.getText().equals("") || txtPassword.getText().equals("")
							|| txtNombre.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "CAMPOS VACIOS");
						return;
					}
					usuario.setUser(txtUser.getText());
					usuario.setPassword(txtPassword.getText());
					usuario.setNombre(txtNombre.getText());
					if (dao.editarUsuario(usuario)) {
						actualizarTabla();
						limpiar();
						JOptionPane.showMessageDialog(null, "SE ACTUALIZO CORRECTAMENTE!!");
					} else {
						JOptionPane.showMessageDialog(null, "ERROR");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "ERROR");
				}
			}
		});
		btnActualizar.setBounds(121, 280, 89, 23);
		contentPane.add(btnActualizar);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnLimpiar.setBounds(121, 315, 89, 23);
		contentPane.add(btnLimpiar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(322, 11, 522, 359);
		contentPane.add(scrollPane);

		tblUsuarios = new JTable();
		tblUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fila = tblUsuarios.getSelectedRow();
				usuario = lista.get(fila);
				lblId.setText("" + usuario.getId());
				txtUser.setText(usuario.getUser());
				txtPassword.setText(usuario.getPassword());
				txtNombre.setText(usuario.getNombre());

			}
		});
		tblUsuarios.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, },
				new String[] { "New column", "New column", "New column", "New column" }));
		scrollPane.setViewportView(tblUsuarios);
		modelo.addColumn("ID");
		modelo.addColumn("USUARIO");
		modelo.addColumn("PASSWORD");
		modelo.addColumn("NOMBRE");
		actualizarTabla();
	}
}
