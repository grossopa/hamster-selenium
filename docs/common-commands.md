# Hamster Selenium 常用命令

## 构建命令

```bash
# 完整构建（含测试）
mvn clean verify

# 完整构建（含覆盖率报告，CI 使用）
mvn clean verify -Pcoverage

# 构建单个模块
mvn test -pl hamster-selenium-core

# 构建单个模块（含依赖）
mvn test -pl hamster-selenium-component-materialui -am
```

## License 检查

```bash
# 检查所有文件是否有正确的 license 头
mvn com.mycila:license-maven-plugin:check

# 自动补全缺失的 license 头
mvn com.mycila:license-maven-plugin:format
```

## Checkstyle 检查

```bash
# 检查 Javadoc 规范和代码风格
mvn org.apache.maven.plugins:maven-checkstyle-plugin:check

# 检查单个模块
mvn org.apache.maven.plugins:maven-checkstyle-plugin:check -pl hamster-selenium-core
```

## 测试命令

```bash
# 运行所有测试
mvn test

# 运行所有测试（含覆盖率）
mvn test -Pcoverage

# 运行单个模块测试
mvn test -pl hamster-playwright-core
```

## 发布命令

```bash
# 准备发布
mvn release:prepare

# 执行发布
mvn release:perform

# 发布（含 source/javadoc/gpg 签名）
mvn deploy -Prelease
```

## 代码质量

```bash
# 生成覆盖率报告（JaCoCo XML）
mvn verify -Pcoverage

# SonarCloud 分析
mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Pcoverage

# 变异测试（PITest）
mvn org.pitest:pitest-maven:mutationCoverage -pl <module>
```

## 环境要求

- **JDK**: 17（编译和测试必须使用 JDK 17）
- **Maven**: 3.x
- **CI**: GitHub Actions 使用 JDK 17 Temurin
