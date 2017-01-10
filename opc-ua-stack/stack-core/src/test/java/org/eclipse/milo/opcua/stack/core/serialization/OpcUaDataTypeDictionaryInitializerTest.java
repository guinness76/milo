package org.eclipse.milo.opcua.stack.core.serialization;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

public class OpcUaDataTypeDictionaryInitializerTest {

    @Test
    public void testInitialize() throws Exception {
        OpcUaDataTypeDictionaryInitializer.initialize();

        ClassLoader classLoader = getClass().getClassLoader();
        ClassPath classPath = ClassPath.from(classLoader);

        ImmutableSet<ClassPath.ClassInfo> structures =
            classPath.getTopLevelClasses("org.eclipse.milo.opcua.stack.core.types.structured");

        assertNotEquals(structures.size(), 0);

        for (ClassPath.ClassInfo classInfo : structures) {
            Class<?> clazz = classInfo.load();

            assertNotNull(
                OpcUaDataTypeDictionary.getInstance().getBinaryCodec(clazz.getSimpleName()),
                "no binary codec found for " + clazz.getSimpleName()
            );

            assertNotNull(
                OpcUaDataTypeDictionary.getInstance().getXmlCodec(clazz.getSimpleName()),
                "no xml codec found for " + clazz.getSimpleName()
            );
        }
    }

}