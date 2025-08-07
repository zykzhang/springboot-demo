package com.itheima.mapper;

import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

    //根据id删除数据
    //mybatis提供了#{} 用来动态接受方法的参数
    @Delete("delete from emp where id = #{id}")
    public void delete(Integer id);  // delete insert语句会返回一个本次操作影响的记录数 这里不用就不写成void

    //插入操作
    @Options(keyProperty = "id", useGeneratedKeys = true)  //主键返回：会自动将生成的主键值，赋值给emp对象的id属性
    @Insert("insert into emp (username, name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    public void insert(Emp emp);

    //public Emp selectById(Integer id);

    //条件查询
//    @Select("select * from emp where name like concat('%',#{name},'%') and gender = #{gender} and " +
//            "entrydate between #{begin} and #{end} order by update_time desc")
//    public List<Emp> list(String name, short gender, LocalDate begin, LocalDate end);

    /*
    使用注解来映射简单语句会使代码显得更加简洁，但对于稍做复杂一点的语句，Javà 注解不仅力不从心，
    还会让你本就复杂的 SOL 语句更加混乱不堪。因此，如果你需要做一些很复杂的操作，最好用 XML 来映射语句。

     */
    //使用xml映射文件
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    public void update2(Emp emp);


}
