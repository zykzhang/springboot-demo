package com.itheima;

import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class SpringbootMybatisCrudApplicationTests {

    @Autowired
    private EmpMapper empMapper;

    @Test
    public void testDelete(){
        empMapper.delete(16);
    }

    @Test
    public void testInsert(){
        Emp emp = new Emp();
        emp.setUsername("Tom2");
        emp.setName("汤姆2");
        emp.setImage("1.jpg");
        emp.setGender((short)1);
        emp.setJob((short)1);
        emp.setEntrydate(LocalDate.of(2000,1,1));
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        emp.setDeptId(1);
        empMapper.insert(emp);
        System.out.println(emp.getId());


        }
    //根据动态条件查询员工
    @Test
    public void testList(){
        //常规的：参数全部给值
        //List<Emp> reslist = empMapper.list("张", (short)1,LocalDate.of(2010,1,1),LocalDate.of(2020,1,1));
        //测试动态sql-if
         List<Emp> reslist = empMapper.list("张", (short)1 ,null,null);
        System.out.println(reslist);
    }

    //动态更新员工 - 更新ID为18的员工 username 更新为 Tom111, name更新为 汤姆111, gender更新为2
    @Test
    public void testUpdate2(){
        //构造员工对象
        Emp emp = new Emp();
        emp.setId(18);
        emp.setUsername("Tom111");
        emp.setName("汤姆111");
        emp.setGender((short)1);
        emp.setUpdateTime(LocalDateTime.now());

        //执行更新员工操作
        empMapper.update2(emp);
    }




}
