package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;
import modelo.Usuario;

public class daoUsuario {

	Conexion cx;

	public daoUsuario() {
		cx = new Conexion();
	}

	public boolean insertarUsuario(Usuario user) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement("INSERT INTO usuario VALUES(null,?,?,?)");
			ps.setString(1, user.getUser());
			ps.setString(2, convertirSHA256(user.getPassword()));
			ps.setString(3, user.getNombre());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Usuario> consultaUsuarios() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement("SELECT * FROM usuario");
			rs = ps.executeQuery();
			while (rs.next()) {
				Usuario user = new Usuario();
				user.setId(rs.getInt("id"));
				user.setUser(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setNombre(rs.getString("nombre"));
				lista.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	public boolean eliminarUsuario(int Id) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement("DELETE FROM usuario WHERE id=?");
			ps.setInt(1, Id);
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean editarUsuario(Usuario user) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement("UPDATE usuario SET user=?,password=?,nombre=? WHERE id=?");
			ps.setString(1, user.getUser());
			ps.setString(2, convertirSHA256(user.getPassword()));
			ps.setString(3, user.getNombre());
			ps.setInt(4, user.getId());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public String convertirSHA256(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

		byte[] hash = md.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();

		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

}
