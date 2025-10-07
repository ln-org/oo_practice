# 斗兽棋/Jungle Game 

## 项目结构/Project Structure

``` bash
p2b
├── class_diagram.png   # 类图
├── pyproject.toml      # 项目配置文件
└── tests               # 测试
└── src
    └── jungle          # 源码
```
## 测试/Running Tests 

```bash
# 进入p2b
cd p2b

# 以开发模式安装
pip install -e '.[dev]'

# 测试
pytest tests -v --tb=short
```

## 如何编译/Compile

