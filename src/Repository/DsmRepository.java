package Repository;

import Config.JdbcTemplate;

import Model.Dsm;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DsmRepository {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	private final int batchCountLimit = 4096;

	/**
	 * DsmService 에서 수집한 모든 DSM read stream 배열 전달받음
	 */
	public void save(List<Dsm> dsmList) {
		try(Connection connect = jdbcTemplate.getConnection()) {
			setPsTmt(connect, dsmList);
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setPsTmt(Connection connect, List<Dsm> dsmList) throws SQLException {
		String sql = "INSERT INTO DSM VALUES(?, ?, ?)";
		long totalBatchCount = 0;
		try (PreparedStatement ps = connect.prepareStatement(sql)) {
			for (Dsm dsm : dsmList) {
				long startTime = System.currentTimeMillis();
				System.out.printf("save %s ... ", dsm.getDsmName());
				totalBatchCount += readDsm(ps, dsm);
				dsm.close();
				long endTime = System.currentTimeMillis();
				System.out.println(String.format("코드 실행 시간: %20dms", endTime - startTime));
			}
		} catch (SQLException | IOException e) {
			// DSM 파일의 하나라도 오류가 나면 해당 파일 전체 작업 rollback.
			System.err.printf("오류, 다음 %d 개의 작업 rollback\n", totalBatchCount);
			connect.rollback();
			e.printStackTrace();
		}
		System.out.printf("\ntotalBatchCount : %d\n", totalBatchCount);
	}

	private int readDsm(PreparedStatement ps, Dsm dsm) throws IOException, SQLException {
		BufferedReader reader = dsm.getReader();
		int batchCount = batchCountLimit, batchResult = 0;
		String line;
		while ((line = reader.readLine()) != null) {
			String[] s = line.split(" ");
			ps.setDouble(1, Double.parseDouble(s[0]));
			ps.setDouble(2, Double.parseDouble(s[1]));
			ps.setDouble(3, Double.parseDouble(s[2]));
			ps.addBatch();
			ps.clearParameters();
			if(--batchCount == 0) {
				batchResult += ps.executeBatch().length;
				batchCount = batchCountLimit;
				ps.clearBatch();
			}
		}
		batchResult += ps.executeBatch().length;
		ps.clearBatch();
		System.out.printf("%d records\n", batchResult);
		return batchResult;
	}
}
