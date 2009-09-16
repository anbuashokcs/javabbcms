/**
 * Script de migração phpBB -> JavaBB
 * @author Lucas Teixeira - lucas@javabb.org
 */

package org.javabb.migration.betweendbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jbb_posts extends Jbb_Convert {

	public void convert() throws ClassNotFoundException, SQLException {

		sql = "select * from jbb_posts ";
		System.out.println("TABLE JBB_POSTS.");
		Connection phpConn = this.getDbOrigin();
		Statement stm = phpConn.createStatement();
		System.out.println("\tReading data");
		ResultSet rs = stm.executeQuery(sql);
		long i = 0;

		Connection javaConn = this.getDbDest();

		javaConn.createStatement().executeUpdate("delete from jbb_posts");
		System.out.println("delete from jbb_posts is OK!");

		PreparedStatement ps = javaConn
				.prepareStatement("insert into jbb_posts (id_post, id_user, id_topic, "
						+ "data_post, assunto, sig, ip, post_state) values (?,?,?,?,?,?,?,?)");

		System.out.println("\tInserting data ");
		while (rs.next()) {
			ps.setInt(1, rs.getInt("id_post"));
			ps.setInt(2, rs.getInt("id_user"));
			ps.setInt(3, rs.getInt("id_topic"));
			ps.setTimestamp(4, rs.getTimestamp("data_post"));
			ps.setString(5, rs.getString("assunto"));
			ps.setInt(6, rs.getInt("sig"));
			ps.setString(7, rs.getString("ip"));
			ps.setInt(8, rs.getInt("post_state"));

			ps.addBatch();

			if (i % 10000 == 0) {
				try {
					ps.executeBatch();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				;
			}

			System.out.print(".");
		}
		System.out.println("\n");

		try {
			ps.executeBatch();
		} catch (SQLException e) {
			System.out.println("\n\n" + e.getNextException().getMessage());
		}

		ps.close();
		stm.close();
		rs.close();
		phpConn.close();
		javaConn.close();
		System.out.print("Done!\n\n");
	}
}