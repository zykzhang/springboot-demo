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
        emp.setName("��ķ2");
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
    //���ݶ�̬������ѯԱ��
    @Test
    public void testList(){
        //����ģ�����ȫ����ֵ
        //List<Emp> reslist = empMapper.list("��", (short)1,LocalDate.of(2010,1,1),LocalDate.of(2020,1,1));
        //���Զ�̬sql-if
         List<Emp> reslist = empMapper.list("��", (short)1 ,null,null);
        System.out.println(reslist);
    }

    //��̬����Ա�� - ����IDΪ18��Ա�� username ����Ϊ Tom111, name����Ϊ ��ķ111, gender����Ϊ2
    @Test
    public void testUpdate2(){
        //����Ա������
        Emp emp = new Emp();
        emp.setId(18);
        emp.setUsername("Tom111");
        emp.setName("��ķ111");
        emp.setGender((short)1);
        emp.setUpdateTime(LocalDateTime.now());

        //ִ�и���Ա������
        empMapper.update2(emp);
    }




}
