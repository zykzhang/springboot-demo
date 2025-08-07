package com.itheima.mapper;

import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

    //����idɾ������
    //mybatis�ṩ��#{} ������̬���ܷ����Ĳ���
    @Delete("delete from emp where id = #{id}")
    public void delete(Integer id);  // delete insert���᷵��һ�����β���Ӱ��ļ�¼�� ���ﲻ�þͲ�д��void

    //�������
    @Options(keyProperty = "id", useGeneratedKeys = true)  //�������أ����Զ������ɵ�����ֵ����ֵ��emp�����id����
    @Insert("insert into emp (username, name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    public void insert(Emp emp);

    //public Emp selectById(Integer id);

    //������ѯ
//    @Select("select * from emp where name like concat('%',#{name},'%') and gender = #{gender} and " +
//            "entrydate between #{begin} and #{end} order by update_time desc")
//    public List<Emp> list(String name, short gender, LocalDate begin, LocalDate end);

    /*
    ʹ��ע����ӳ�������ʹ�����Եø��Ӽ�࣬��������������һ�����䣬Jav�� ע�ⲻ���������ģ�
    �������㱾�͸��ӵ� SOL �����ӻ��Ҳ�������ˣ��������Ҫ��һЩ�ܸ��ӵĲ���������� XML ��ӳ����䡣

     */
    //ʹ��xmlӳ���ļ�
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    public void update2(Emp emp);


}
