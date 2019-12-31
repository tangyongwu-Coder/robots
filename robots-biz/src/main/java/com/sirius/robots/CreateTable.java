package com.sirius.robots;

import com.sirius.robots.bo.Fields;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateTable {
	
	private String projectPath = "";
	//作者名字
	private String authorName = "孟星魂";

	private String date = getFormatDate(new Date(),"yyyy-MM-dd");

	private final static String dalJava = "robots-dal.src.main.java.";
    private final static String managerJava = "robots-manager.src.main.java.";

	private final static String dalResources = "robots-dal.src.main.resources.";

	private final static String dalName =  "com.sirius.robots.dal.";
    private final static String managerPackageName =  "com.sirius.robots.manager";


    private final static Boolean managerFlag = Boolean.FALSE;
	//实体类包
	private static String entityPackageBeanName = dalName + "model";

	//实体类包
	private static String entityPackageUrl = dalJava + dalName + "model";


	//sqlMaping文件包
	private final static String configPackageName = dalName + "mapper";

	//sqlMaping文件包
	private final static String configPackageUrl = dalJava + dalName + "mapper";

	//xml文件包
	private final static String configXmlPackageUrl = dalResources + "config.mapper";



    private final static String managerPackageUrl = managerJava+ managerPackageName;



	private final static String dbDriver = "com.mysql.jdbc.Driver";

	private final static String dbURL = "jdbc:mysql://139.196.192.223:3306/ROBOTS?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false";
	private final static String dbUser = "test";
	private final static String dbPass = "Test123456@";

	private final static String CREATED_AT = "CREATED_AT";
	private final static String CREATED_BY = "CREATED_BY";
	private final static String UPDATED_AT = "UPDATED_AT";
	private final static String UPDATED_BY = "UPDATED_BY";
	private final static String[] tables = {"T_USER_INFO",};
	
	
	private static StringBuffer importString=null;
	
	private Map<String,String> _v_tableNameList = null;
	public CreateTable(){
		File directory = new File("");
		projectPath = directory.getAbsolutePath()+"\\";
		System.out.println(projectPath);
		try {
			_v_tableNameList = _m_getDbTableNameList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		CreateTable ct = new CreateTable();
		ct.createAllTable();
	}

	protected String _m_toAttributeString(String _p_attr) {
		if (_p_attr == null) {
			return null;
		}
		String toLowerCase = _p_attr.toLowerCase();
		char[] _v_charArray = toLowerCase.toCharArray();
		if (_v_charArray.length > 0) {
			_v_charArray[0] = Character.toLowerCase(_v_charArray[0]);
			if (_v_charArray.length > 1) {
				_v_charArray[1] = Character.toLowerCase(_v_charArray[1]);
			}
		}
		return new String(_v_charArray);
	}


	/**
	 * 返回指定格式的日期字符串
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormatDate(Date date, String format){
		if (date == null || !StringUtils.isNotBlank(format)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 *  首字母大写
	 * @param _p_String
	 * @return
	 */
	protected String _m_firstToUpperCase(String _p_String) {

		if (_p_String == null) {
			return null;
		}
		String toLowerCase = _p_String.toLowerCase();
		char[] _v_charArray = toLowerCase.toCharArray();
		if (_v_charArray.length > 0) {
			_v_charArray[0] = Character.toUpperCase(_v_charArray[0]);
		}
		return new String(_v_charArray);
	}

    /**
     *  首字母小写
     * @param _p_String
     * @return
     */
    protected String _m_firstToLowerCase(String _p_String) {

        if (_p_String == null) {
            return null;
        }
        char[] _v_charArray = _p_String.toCharArray();
        if (_v_charArray.length > 0) {
            _v_charArray[0] = Character.toLowerCase(_v_charArray[0]);
        }
        return new String(_v_charArray);
    }

	/**
	 *  类名
	 * CHANNEL_CONFIG_INFO ->ChannelConfigInfo
	 * @param tableName
	 * @return
	 */
	private String initClassName(String tableName){
		String toLowerCase = tableName.toLowerCase();
		String[] names = toLowerCase.split("_");
		StringBuffer sb = new StringBuffer();
		for(int i=1;i<names.length;i++){
			sb.append(initCap(names[i]));
		}
		return sb.toString();
	}
	/**
	 *
	 * @param tableName
	 * @return ENCRYPT_TYPE->channelConfigInfo
	 */
	private String initFileName(String tableName){
		String toLowerCase = tableName.toLowerCase();
		String[] names = toLowerCase.split("_");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<names.length;i++){
			if(i==0){
				sb.append(names[i]);
			}else{
				sb.append(initCap(names[i]));
			}
		}
		return sb.toString();
	}

	
	public void createAllTable()throws Exception{
		File _v_folder = new File(projectPath + ""
				+ entityPackageUrl.replace(".", "\\\\"));
		if(!_v_folder.exists()){
			_v_folder.mkdirs();
		}

		_v_folder = new File(projectPath + ""
				+ configPackageUrl.replace(".", "\\\\"));
		if(!_v_folder.exists()) {
			_v_folder.mkdirs();
		}

		_v_folder = new File(projectPath + ""
				+ configXmlPackageUrl.replace(".", "\\\\"));
		if(!_v_folder.exists()) {
			_v_folder.mkdirs();
		}
        for (Map.Entry<String, String> entry : _v_tableNameList.entrySet()) {
            String _v_tblName = entry.getKey();
            String _v_tblDesc = entry.getValue();
            for(String table : tables){
                if(table.equals(_v_tblName)){
                    System.out.println("table name:" + _v_tblName);
                    _m_packEntityAndConfig(_v_tblName,_v_tblDesc);
                    break;
                }
            }
        }
	}


	protected void _m_packEntityAndConfig(String _v_tblName,String _v_tblDesc) throws Exception {
		importString =  new StringBuffer();
		List<Fields> _v_fields = _m_getFields(_v_tblName);
		String tableName = initClassName(_v_tblName);
		String _v_xmlName = _v_tblName.toLowerCase();
		String tableName2 = initFileName(_v_xmlName);
		String _v_fullClsName = entityPackageBeanName + "." + tableName;
		// 打包实体类 start //////////////////////////////
		File _v_jFile = new File(projectPath + ""
				+ entityPackageUrl.replace(".", "\\\\") + "\\\\" + tableName
				+ ".java");
		if (!_v_jFile.exists()) {
			_v_jFile.createNewFile();
		}
		PrintWriter _v_fw = new PrintWriter(_v_jFile, "UTF-8");
		_v_fw.println("package " + entityPackageBeanName + ";\n");
		_v_fw.println("import lombok.Data;\n");
		_v_fw.println("import java.io.Serializable;\n");
		_v_fw.println(importString.toString());
		_v_fw.println("/**" +
				"\r\n * "+_v_tblDesc+"实体类" +
				"\r\n * " +
				"\r\n * @author "+authorName+"" +
				"\r\n * @version 5.0 createTime: "+date+"" +
				"\r\n  */" +
				"\r\n@Data");
		_v_fw.println("public class "
						+ tableName
						+ " extends BaseDO implements Serializable {\n");
		_v_fw.println("\tprivate static final long serialVersionUID="+System.currentTimeMillis()+"L;");
		for (Fields _v_f : _v_fields){
            if(_v_f.isPrimaryKey()){
                continue;
            }
            if(UPDATED_AT.equals(_v_f.getName())){
                continue;
            }
            if(UPDATED_BY.equals(_v_f.getName())){
                continue;
            }
            if(CREATED_AT.equals(_v_f.getName())){
                continue;
            }else if(CREATED_BY.equals(_v_f.getName())) {
                continue;
            }
			_v_fw.println("   /**\r\n"+"    * "+_v_f.getComment()+"\r\n"+"    */");
			_v_fw.println("\tprivate " + _v_f.getJavaType() + " "
					+ initFileName(_v_f.getName()) + ";");
		}
		_v_fw.println("}");
		_v_fw.flush();
		_v_fw.close();
		// 打包实体类 end //////////////////////////////
		// 打包MAPPER类 start //////////////////////////////
		_v_jFile = new File(projectPath + ""
				+ configPackageUrl.replace(".", "\\\\") + "\\\\" + tableName
				+ "Mapper.java");
		if (!_v_jFile.exists()) {
			_v_jFile.createNewFile();
		}
		_v_fw = new PrintWriter(_v_jFile, "UTF-8");
		_v_fw.println("package " + configPackageName + ";\n");
		_v_fw.println("import "+_v_fullClsName+";");
		_v_fw.println("/**" +
				"\r\n * "+_v_tblDesc+"数据库操作" +
				"\r\n * " +
				"\r\n * @author "+authorName+"" +
				"\r\n * @version 5.0 createTime: "+date+"" +
				"\r\n  */");
		_v_fw.println("public interface "
				+ tableName+"Mapper extends BaseMapper<"+tableName+">{\n");
		_v_fw.println("}");
		_v_fw.flush();
		_v_fw.close();
		// 打包MAPPER end //////////////////////////////


		// 打包sql xml文件 start //////////////////////////////
		_v_jFile = new File(projectPath + ""
				+ configXmlPackageUrl.replace(".", "\\\\") + "\\\\" + tableName
				+ "Mapper.xml");
		if (!_v_jFile.exists()) {
			_v_jFile.createNewFile();
		}
		_v_fw = new PrintWriter(_v_jFile, "UTF-8");
		_v_fw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"\r\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">"
						+ "\r\n<mapper namespace=\""+ configPackageName + "."+tableName+"Mapper\">\r");
		String resultMapString = "\t<resultMap id=\"BaseResultMap\" type=\""+_v_fullClsName+"\">";
		for (Fields _v_fd : _v_fields) {
			resultMapString+="\r\n\t\t<result column=\""
					+_v_fd.getName()
					+ "\"\t\t jdbcType=\""+_v_fd.getType()
					+ "\"\t\t property=\""
					+ initFileName(_v_fd.getName())
					+ "\"/>";
		}
		resultMapString+="\r\n\t</resultMap>\n";
		_v_fw.println(resultMapString);
		String sqlString = "\t<sql id=\"Base_Column_List\">\r\n\t\t";
		for (int i=0;i<_v_fields.size();i++) {
			Fields _v_fd = _v_fields.get(i);
			if(i==_v_fields.size()-1){
				sqlString+=" "+_v_fd.getName();
			}else{
				sqlString+=" "+_v_fd.getName()+",";
			}
			if(i==5||i==10||i==15||i==20){
				sqlString+="\r\n\t\t";
			}
		}
		sqlString+="\n\t</sql>\n";
		_v_fw.println(sqlString);
		String _v_content;
		// === select by primary key ===
		String _v_pkColName = _m_toAttributeString(_m_getPrimaryKeyColumn(_v_fields));
		if (_v_pkColName != null) {
			_v_content = "\t<select id=\"selectByPrimaryKey\" parameterType=\"java.lang.Integer\" resultMap=\"BaseResultMap\">" +
					"\r\n\t\tSELECT" +
					"\r\n\t\t<include refid=\"Base_Column_List\" />" +
					"\r\n\t\tFROM "+_v_tblName+"" +
					"\r\n\t\tWHERE ID = #{id,jdbcType=INTEGER}" +
					"\r\n\t</select>";
			_v_fw.println(_v_content);
		}
		// === insert record ===
		_v_content = "\t<insert id=\"insert\" parameterType=\""+_v_fullClsName+"\" useGeneratedKeys=\"true\"" +
				"\r\n\t\t\t\tkeyProperty=\"id\">";

		_v_content += "\r\n\t\tINSERT INTO " + _v_tblName+"(\r\n\t\t";
		for (int i=0;i<_v_fields.size();i++) {
			Fields _v_fd = _v_fields.get(i);
			if(_v_fd.isPrimaryKey()){
				continue;
			}
			if(UPDATED_AT.equals(_v_fd.getName())){
				continue;
			}
			if(UPDATED_BY.equals(_v_fd.getName())){
				continue;
			}
			if(CREATED_BY.equals(_v_fd.getName())){
				_v_content+=" "+_v_fd.getName();
			}else{
				_v_content+=" "+_v_fd.getName()+",";
			}
			if(i==5||i==10||i==15||i==20){
				_v_content+="\r\n\t\t";
			}
		}
		_v_content += "\r\n\t\t )VALUES( \r\n\t\t\t";
		for (int i=0;i<_v_fields.size();i++) {
			Fields _v_fd = _v_fields.get(i);
			if(_v_fd.isPrimaryKey()){
				continue;
			}
			if(UPDATED_AT.equals(_v_fd.getName())){
				continue;
			}
			if(UPDATED_BY.equals(_v_fd.getName())){
				continue;
			}
			if(CREATED_AT.equals(_v_fd.getName())){
				_v_content+="CURRENT_TIMESTAMP,";
			}else if(CREATED_BY.equals(_v_fd.getName())){
				_v_content+="#{"+initFileName(_v_fd.getName())+",jdbcType="+_v_fd.getType()+"}";
			}else{
				_v_content+="#{"+initFileName(_v_fd.getName())+",jdbcType="+_v_fd.getType()+"},";
			}
			if(i==5||i==10||i==15||i==20){
				_v_content+="\r\n\t\t";
			}
		}
		_v_content += "\r\n\t\t)\r\n";
		_v_content += "\t</insert>\r\n";
		_v_fw.println(_v_content);
		// === insertBatch record ===
		_v_content = "\t<insert id=\"insertBatch\" parameterType=\"java.util.List\" useGeneratedKeys=\"true\"\n>";

		_v_content += "\r\n\t\tINSERT INTO " + _v_tblName+"(\r\n\t\t";
		for (int i=0;i<_v_fields.size();i++) {
			Fields _v_fd = _v_fields.get(i);
			if(_v_fd.isPrimaryKey()){
				continue;
			}
			if(UPDATED_AT.equals(_v_fd.getName())){
				continue;
			}
			if(UPDATED_BY.equals(_v_fd.getName())){
				continue;
			}
			if(CREATED_BY.equals(_v_fd.getName())){
				_v_content+=" "+_v_fd.getName();
			}else{
				_v_content+=" "+_v_fd.getName()+",";
			}
			if(i==5||i==10||i==15||i==20){
				_v_content+="\r\n\t\t";
			}
		}
		_v_content += "\r\n\t\t )VALUES";
		_v_content +="\r\n\t\t<foreach collection=\"list\" index=\"index\" item=\"item\" separator=\",\">";
		_v_content += "\r\n\t\t(\r\n\t\t";
		for (int i=0;i<_v_fields.size();i++) {
			Fields _v_fd = _v_fields.get(i);
			if(_v_fd.isPrimaryKey()){
				continue;
			}
			if(UPDATED_AT.equals(_v_fd.getName())){
				continue;
			}
			if(UPDATED_BY.equals(_v_fd.getName())){
				continue;
			}
			if(CREATED_AT.equals(_v_fd.getName())){
				_v_content+="CURRENT_TIMESTAMP,";
			}else if(CREATED_BY.equals(_v_fd.getName())){
				_v_content+="#{item."+initFileName(_v_fd.getName())+",jdbcType="+_v_fd.getType()+"}";
			}else{
				_v_content+="#{item."+initFileName(_v_fd.getName())+",jdbcType="+_v_fd.getType()+"},";
			}
			if(i==5||i==10||i==15||i==20){
				_v_content+="\r\n\t\t";
			}
		}
		_v_content += "\r\n\t\t)\r\n";

		_v_content += "\t\t</foreach>\r\n";
		_v_content += "\t</insert>";
		_v_fw.println(_v_content);

		// === update record ===
		if (_v_pkColName != null) {
			_v_content = "\r\n\t<update id=\""+"updateByPrimaryKeySelective"
					+ "\" parameterType=\"" + _v_fullClsName + "\">\r\n"
					+ "\t\tUPDATE " + _v_tblName
					+ "\r\n\t\t<set>";
			for (Fields _v_fd : _v_fields) {
				if(_v_fd.isPrimaryKey()){
					continue;
				}
				if(CREATED_AT.equals(_v_fd.getName())){
					continue;
				}
				if(CREATED_BY.equals(_v_fd.getName())){
					continue;
				}
				if(UPDATED_AT.equals(_v_fd.getName())){
					_v_content+="\r\n\t\t\tUPDATED_AT = CURRENT_TIMESTAMP,";
					continue;
				}
				if (!_v_pkColName.equals(_v_fd.getName())) {
					_v_content += "\r\n\t\t\t<if test=\""+initFileName(_v_fd.getName())+" != null\">\r\n\t\t" +
							"        "+_v_fd.getName()+" = #{"+initFileName(_v_fd.getName())+",jdbcType="+_v_fd.getType()+"}," +
							"\r\n\t\t\t</if>";
				}
			}
			//_v_content+=  WHERE id = #id#;
			_v_content += "\r\n\t\t</set>\r\n\t\tWHERE ID = #{id,jdbcType=INTEGER}"
					+ "\r\n\t</update>";
			_v_fw.println(_v_content);
		}
		// === delete record ===
		if (_v_pkColName != null) {
			_v_content = "\r\n\t<delete id=\"delete\" parameterType=\""+_v_fullClsName+"\">" +
					"\r\n\t\tUPDATE "+_v_tblName+"" +
					"\r\n\t\tSET" +
					"\r\n\t\t<if test=\"updatedBy != null\">" +
					"\r\n\t\t\tUPDATED_BY = #{updatedBy,jdbcType=VARCHAR}," +
					"\r\n\t\t</if>" +
					"\r\n\t\tUPDATED_AT = CURRENT_TIMESTAMP," +
					"\r\n\t\tDELETE_FLAG=1" +
					"\r\n\t\tWHERE id=#{id,jdbcType=INTEGER}" +
					"\r\n\t</delete>";
			_v_fw.println(_v_content);
		}
		if (_v_pkColName != null) {
			_v_content = "\r\n\t<select id=\"selectBySelective\" parameterType=\"" + _v_fullClsName + "\"" +
					"\r\n\t\t\t\t\tresultMap=\"BaseResultMap\">" +
					"\r\n\t\t\tSELECT" +
					"\r\n\t\t\t<include refid=\"Base_Column_List\"/>" +
					"\r\n\t\t\tFROM "+_v_tblName;
			_v_content +="\r\n\t\t\t<where>";
			for (Fields _v_fd : _v_fields) {
				if(_v_fd.getType().equals("VARCHAR")){
					_v_content += "\r\n\t\t\t\t<if test=\""+initFileName(_v_fd.getName())+" != null and "+initFileName(_v_fd.getName())+" != ''\">";
				}else{
					_v_content += "\r\n\t\t\t\t<if test=\""+initFileName(_v_fd.getName())+" != null\">";
				}
				_v_content += "\r\n\t\t\t\tAND "+_v_fd.getName()+" = #{"+initFileName(_v_fd.getName())+",jdbcType="+_v_fd.getType()+"}" +
						"\r\n\t\t\t\t</if>";
			}
			_v_content += "\r\n\t\t</where>\r\n\t\tORDER BY ID DESC\r\n\t</select>";
		}
		_v_fw.println(_v_content);
        _v_fw.println("\r\n</mapper>");
		_v_fw.flush();
		_v_fw.close();
		// 打包sql xml文件 end //////////////////////////////
        if(managerFlag){
            managerConfig(_v_tblName,_v_tblDesc);
        }
	}


	private void managerConfig(String _v_tblName,String _v_tblDesc) throws Exception{
        String tableName = initClassName(_v_tblName);
        String managerName = tableName.replaceAll("Info","")+"Manager1";
        String _v_xmlName = _v_tblName.toLowerCase();
        // 打包管理类 start //////////////////////////////
        File _v_jFile = new File(projectPath + ""
                + managerPackageUrl.replace(".", "\\\\") + "\\\\" + managerName
                + ".java");
        if (!_v_jFile.exists()) {
            _v_jFile.createNewFile();
        }
        String mapperName = tableName+"Mapper";
        String mapperBeanName = _m_firstToLowerCase(mapperName);
        String doBeanName = _m_firstToLowerCase(tableName);

        String a = "package "+managerPackageName+";\n" +
                "\n" +

                "import "+configPackageName + "."+mapperName+";\n" +
                "import "+entityPackageBeanName+"."+tableName+";\n" +
                "import lombok.extern.slf4j.Slf4j;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Component;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "/**\n" +
                " * "+_v_tblDesc.replaceAll("表","")+"管理" +
                "\r\n *" +
                "\r\n * @author "+authorName+"" +
                "\r\n * @version 5.0 createTime: "+date+"" +
                "\r\n */" +
                "\r\n@Slf4j\n" +
                "@Component\n" +
                "public class "+managerName+" {\n" +
                "\n" +
                "    @Autowired\n" +
                "    private "+mapperName+" "+mapperBeanName+";\n" +
                "\n" +
                "    /**\n" +
                "     * 新增\n" +
                "     *\n" +
                "     * @param "+doBeanName+"  "+_v_tblDesc+"实体类\n" +
                "     */\n" +
                "    public void add("+tableName+" "+doBeanName+"){\n" +
                "        "+mapperBeanName+".insert("+doBeanName+");\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 修改\n" +
                "     *\n" +
                "     * @param "+doBeanName+"  "+_v_tblDesc+"实体类\n" +
                "     */\n" +
                "    public void edit("+tableName+" "+doBeanName+"){\n" +
                "        "+mapperBeanName+".updateByPrimaryKeySelective("+doBeanName+");\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 删除\n" +
                "     *\n" +
                "     * @param "+doBeanName+"  "+_v_tblDesc+"实体类\n" +
                "     */\n" +
                "    public void delete("+tableName+" "+doBeanName+"){\n" +
                "        "+mapperBeanName+".delete("+doBeanName+");\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 批量新增\n" +
                "     *\n" +
                "     * @param list  "+_v_tblDesc+"实体类\n" +
                "     */\n" +
                "    public void insertBatch(List<"+tableName+"> list){\n" +
                "        "+mapperBeanName+".insertBatch(list);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 查询\n" +
                "     * @param query 查询条件\n" +
                "     * @return      查询结果\n" +
                "     */\n" +
                "    public List<"+tableName+"> selectBySelective("+tableName+" query){\n" +
                "        return "+mapperBeanName+".selectBySelective(query);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 主键查询\n" +
                "     * @param id    主键\n" +
                "     * @return      查询结果\n" +
                "     */\n" +
                "    public "+tableName+" selectByPrimaryKey(Long id){\n" +
                "        return "+mapperBeanName+".selectByPrimaryKey(id);\n" +
                "    }\n" +
                "\n" +
                "}";
        PrintWriter _v_fw = new PrintWriter(_v_jFile, "UTF-8");
        _v_fw.println(a);
        _v_fw.flush();
        _v_fw.close();
    }

	protected String _m_getPrimaryKeyColumn(List<Fields> fieldss) {
		String pkName = null;
		for(Fields fields : fieldss){
			if (fields.isPrimaryKey()) {
				pkName = fields.getName();
				break;
			}	
		}
		return pkName;
	}


	protected List<Fields> _m_getFields(String tableName) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		List<Fields> fieldss = null;
		conn = _m_getConnection();
		ResultSet rs1 = conn.getMetaData()
				.getPrimaryKeys(null, null, tableName);
		String primaryKey = null;
		while (rs1.next()) {
			primaryKey = rs1.getString(4);
		}
		System.out.println("primaryKey=="+primaryKey);
		String sql = "SELECT distinct COLUMN_NAME,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,IS_NULLABLE,CASE WHEN EXTRA = 'auto_increment' THEN 1 ELSE 0 END AS autoIncrease,COLUMN_DEFAULT,COLUMN_COMMENT FROM information_schema.COLUMNS WHERE TABLE_NAME='"+tableName+"'";
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		fieldss = new ArrayList<Fields>();
		while(rs.next()){
			String colName = rs.getString("COLUMN_NAME");
			String colType = rs.getString("DATA_TYPE");
			boolean isPK = colName.equals(primaryKey);
			Fields fields = new Fields(colName,colType, isPK);
			System.out.println(fields.toString());
			Boolean isHave = false;
			for(Fields f :fieldss){
				if(f.getName().equals(colName)){
					isHave = true;
					break;
				}
			}
			if(!isHave){
				fields.setAutoIncrease(rs.getInt("autoIncrease")==1);
				fields.setComment(rs.getString("COLUMN_COMMENT"));
				sqlType2JavaType(fields);
				fieldss.add(fields);
			}
				
		}
		
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
		return fieldss;
	}
	
	/**
	 * 功能：获得列的数据类型
	 * @param fields
	 * @return
	 */
	private void sqlType2JavaType(Fields fields) {
		String sqlType = fields.getType();
		String newType = sqlType.toUpperCase();
        String name = fields.getName();
        //	System.out.println("sqlType==="+sqlType);
		String javaType = null;
		if(sqlType.equalsIgnoreCase("bit")){
			javaType = "Boolean";
		}else if(sqlType.equalsIgnoreCase("tinyint")){
			javaType =  "Integer";
			if(!importString.toString().contains("java.lang.Integer")){
				importString.append("import java.lang.Integer;\r\n");
			}
		}else if(sqlType.equalsIgnoreCase("smallint")){
			javaType = "Integer";
			if(!importString.toString().contains("java.lang.Integer")){
				importString.append("import java.lang.Integer;\r\n");
			}
		}else if(sqlType.equalsIgnoreCase("int")){
			javaType = "Integer";
			newType = "INTEGER";
			if(!importString.toString().contains("java.lang.Integer")){
				importString.append("import java.lang.Integer;\r\n");
			}
		}else if(sqlType.equalsIgnoreCase("bigint")){
			javaType = "Long";
		}else if(sqlType.equalsIgnoreCase("float")){
			javaType = "Long";
			if(!importString.toString().contains("java.lang.Long")){
				importString.append("import java.lang.Long;\r\n");
			}
		}else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") 
				|| sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money") 
				|| sqlType.equalsIgnoreCase("smallmoney")){
			javaType = "Long";
		}else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char") 
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar") 
				|| sqlType.equalsIgnoreCase("text")){
			javaType = "String";
		}else if(sqlType.equalsIgnoreCase("datetime")){
			javaType = "Date";
			if(!importString.toString().contains("java.util.Date")){
				importString.append("import java.util.Date;\r\n");
			}
		}else if(sqlType.equalsIgnoreCase("image")){
			javaType = "Blod";
		}else if(sqlType.equalsIgnoreCase("TIMESTAMP")){
			javaType = "Date";
            if(!UPDATED_AT.equals(name) && !CREATED_AT.equals(name)){
                if(!importString.toString().contains("java.util.Date")){
                    importString.append("import java.util.Date;\r\n");
                }
            }
		}else if(sqlType.equalsIgnoreCase("blob")){
			newType = "LONGVARBINARY";
			javaType = "byte[]";
		}
		if(javaType==null){
			System.out.println("sqlType==="+sqlType);
			javaType = "String";
		}
		fields.setJavaType(javaType);
		fields.setType(newType);
	}
	
	protected Map<String,String> _m_getDbTableNameList() throws Exception {
        Map<String,String> _v_tableNameList = new HashMap<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = _m_getConnection();
		
		stmt = conn.prepareStatement("SHOW TABLE STATUS");
		rs = stmt.executeQuery();
		while (rs.next()) {
            String colName = rs.getString("NAME");
            String colComment = rs.getString("COMMENT");
			_v_tableNameList.put(colName,colComment);
		}
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
		if (rs != null) {
			rs.close();
		}
		return _v_tableNameList;
	}

	protected Connection _m_getConnection() throws Exception {
		Class.forName(dbDriver);
		Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
		return conn;
	}
	

	/**
	 * 功能：将输入字符串的首字母改成大写
	 * @param str
	 * @return
	 */
	private String initCap(String str) {
		
		char[] ch = str.toCharArray();
		if(ch[0] >= 'a' && ch[0] <= 'z'){
			ch[0] = (char)(ch[0] - 32);
		}
		
		return new String(ch);
	}
}

