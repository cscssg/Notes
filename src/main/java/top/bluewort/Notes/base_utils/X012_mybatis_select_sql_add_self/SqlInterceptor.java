package top.bluewort.Notes.base_utils.X012_mybatis_select_sql_add_self;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})})
@Slf4j
@Component
public class SqlInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(SqlInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //1. 拦截预执行方法 获取处理类
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler,new DefaultObjectFactory(),new DefaultObjectWrapperFactory(),new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");

        //2. 获取基本参数 处理方法 类路径 方法名 预执行sql
        //2.1 类路径
        String classPath =mappedStatement.getId().substring(0,mappedStatement.getId().lastIndexOf("."));
        //2.2 方法名
        String methName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".")+1);
        //2.3 预执行sql
        String newSql = boundSql.getSql();
        logger.info("sql:{}",newSql);

        //3. 修改sql语句（核心） 拦截范围1.mybatis-plus包中的类全部进行sql 拦截  2.自定义包 依据注解拦截（方法级别）
        //仅仅拦截 查询语句
        newSql = newSql.replaceAll(" SELECT "," select ").replaceAll(" LIMIT "," limit ")
                .replaceAll(" WHERE "," where ").replaceAll(" ORDER "," order ");
        if(newSql.contains("select")) {
            String delFilter = "";
            if (!containMethod(classPath, methName)) {
                //3.1 mybatis-plus包中的类全部进行sql 拦截
                if(!newSql.contains(" limit ")) {
                    if (!newSql.contains(" order ")) {
                        if (newSql.contains(" where ")) {
                            newSql = newSql + " and (del_flag is null or del_flag = 0) "+ delFilter+ " ";
                        } else {
                            newSql = newSql + " where (del_flag is null or del_flag = 0) "+ delFilter+ " ";
                        }
                    } else {
                        String orderBy = newSql.substring(newSql.lastIndexOf("order"));
                        newSql = newSql.substring(0, newSql.lastIndexOf("order"));
                        if (newSql.contains(" where ")) {
                            newSql = newSql + " and (del_flag is null or del_flag = 0 ) "+ delFilter+ " " + orderBy;
                        } else {
                            newSql = newSql + " where (del_flag is null or del_flag = 0 ) "+ delFilter+ " " + orderBy;
                        }
                    }
                }else {
                    String limit = newSql.substring(newSql.lastIndexOf("limit"));
                    newSql = newSql.substring(0, newSql.lastIndexOf("limit"));
                    if (!newSql.contains(" order ")) {
                        if (newSql.contains(" where ")) {
                            newSql = newSql + " and (del_flag is null or del_flag = 0 ) "+ delFilter+ " ";
                        } else {
                            newSql = newSql + " where (del_flag is null or del_flag = 0 ) "+ delFilter+ " ";
                        }
                    } else {
                        String orderBy = newSql.substring(newSql.lastIndexOf("order"));
                        newSql = newSql.substring(0, newSql.lastIndexOf("order"));
                        if (newSql.contains(" where ")) {
                            newSql = newSql + " and (del_flag is null or del_flag = 0 ) "+ delFilter+ " " + orderBy;
                        } else {
                            newSql = newSql + " where (del_flag is null or del_flag = 0 ) "+ delFilter+ " " + orderBy;
                        }
                    }
                    newSql = newSql + limit;
                }
            }
        }
        logger.info("sql修改：" + newSql);

        //4. 修改后的sql注入执行方法
        if(!StringUtils.isEmpty(newSql)){
            //4.1 通过反射 把修改后的语句 放进去
            Field field = boundSql.getClass().getDeclaredField("sql");
            field.setAccessible(true);
            field.set(boundSql,newSql);
        }

        //5. 继续执行下一个流程
        return invocation.proceed();
    }

    /**
     * 查询方法是否在类中存在
     * @param classPath
     * @param methodName
     * @return
     * @throws ClassNotFoundException
     */
    private boolean containMethod(String classPath,String methodName)throws ClassNotFoundException{
        Class<?> aClass = Class.forName(classPath);
        for(Method me:aClass.getDeclaredMethods()){
            if(me.getName().equals(methodName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
