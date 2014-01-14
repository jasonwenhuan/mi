package com.hyron.db;

import java.lang.reflect.Method;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class HibernateTestBase extends AbstractDependencyInjectionSpringContextTests {

    protected String[] getConfigLocations() {
        return new String[]{
                "classpath:applicationContext-jdbc.xml",
        };
    }


    protected void callAllGetMethodsWithDefault(Object instance) throws Exception {
        final Object[] EMPTY = {};
        @SuppressWarnings("rawtypes")
		Class instClass = instance.getClass();
        for (Method method : instClass.getDeclaredMethods()) {
            if (method.getName().startsWith("get") ||
                    method.getName().startsWith("is") ||
                    method.getName().startsWith("retrieve") ||
                    method.getName().startsWith("search")) {
                try {

                    method.setAccessible(true);
                    @SuppressWarnings("rawtypes")
					Class[] paraClasses = method.getParameterTypes();
                    Object[] params = new Object[paraClasses.length];
                    String args = "(";
                    for (int i = 0; i < paraClasses.length; i++) {
                        params[i] = null;// UnitTestHelper.getDefaultValue(paraClasses[i]);
                        args += params[i];
                        if (i < paraClasses.length - 1) {
                            args += ",";
                        }
                    }
                    args += ")";
                    System.out.println("Calling method: " + instClass.getName() + "." + method.getName() + args);
                    Object returned = method.invoke(instance, (paraClasses.length > 0 ? params : EMPTY));

                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            } else {
                System.out.println("Skipped Method : " + instClass.getName() + "." + method.getName());
            }
        }
    }

}
