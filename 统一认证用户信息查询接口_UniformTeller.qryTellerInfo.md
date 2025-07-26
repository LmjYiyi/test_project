# UniformTeller.qryTellerInfo 接口测试方案

## 接口信息
*   **接口名称**：UniformTeller.qryTellerInfo
*   **接口中文名**：统一认证用户信息查询
*   **请求路径**：`http://192.168.8.123:8080/api/aam/uniformteller/qrytellerInfo/V1`
*   **请求方法**：POST

## 通用请求头
| 参数名        | 类型   | 是否必输 | 描述     | 示例值   |
|-------------|------|--------|--------|--------|
| X-Request-App | string | 是      | 请求应用名 | F-CCPS |
| X-Request-Id  | string | 是      | 请求应用号 | 2010434 |

## 通用响应参数
| 参数名      | 类型   | 是否必输 | 描述             | 示例值         |
|-----------|------|--------|----------------|--------------|
| return_code | string | 是      | 返回码，成功返回0，失败返回非0 | 0            |
| return_msg  | string | 是      | 返回信息         | 请求处理成功     |
| data        | object | 否      | 返回对象         | --           |
| ├─ field1    | string | 是      | 信息1            | 示例信息值     |
| ├─ field2    | object | 否      | 信息2            | --           |
| │  ├─ sub_field1 | int    | 是      | 子信息1          | 100          |
| │  └─ sub_field2 | bool   | 否      | 子信息2          | true         |
| └─ field3    | string | 是      | 信息3            | 另一示例值     |

## 测试场景

###  成功场景：完整请求参数
**描述**：验证接口在所有必填参数和可选参数都提供且有效的情况下，能够成功查询用户信息并返回预期数据。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "1",
  "ssicId": "392201198902121234",
  "username": "john.doe",
  "email": "user@example.com",
  "phone": "+8613812345678",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4",
    "timestamp": "2019-07-01 09:01:01"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "return_code": "0",
  "return_msg": "请求处理成功",
  "data": {
    "field1": "示例信息值",
    "field2": {
      "sub_field1": 100,
      "sub_field2": true
    },
    "field3": "另一示例值"
  }
}
```
### 场景分支1：验证统一认证号分支
**描述**：测试 `ssicType` 为 "1" (统一认证号) 的认证结果。
**准备条件（GIVEN）**：获取开关表里接口名称为UniformTeller.qryTellerInfo的那条信息，将status字段设置成1-启用。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "1",
  "ssicId": "123456789",
  "username": "张三",
  "email": "zhangsan@example.com",
  "phone": "13800138001",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4",
    "timestamp": "2019-07-01 09:01:01"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "data": {
    "field1": "统一认证用户-123456789",
    "field2": {
      "sub_field1": 0,
      "sub_field2": false
    },
    "field3": "服务站点: 总行"
  },
  "return_code": "0",
  "return_msg": "请求处理成功"
}
```

### 场景分支2：验证身份证信息分支
**描述**：测试 `ssicType` 为 "2" (身份证) 的认证结果。
**准备条件（GIVEN）**：获取开关表里接口名称为UniformTeller.qryTellerInfo的那条信息，将status字段设置成1-启用。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "2",
  "ssicId": "110101199001011234",
  "username": "王五",
  "email": "wangwu@example.com",
  "phone": "13800138003",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4",
    "timestamp": "2019-07-01 09:01:01"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "data": {
    "field1": "身份证用户-110101199001011234",
    "field2": {
      "sub_field1": 0,
      "sub_field2": false
    },
    "field3": "服务站点: 支行"
  },
  "return_code": "0",
  "return_msg": "请求处理成功"
}
```

### 场景分支3：验证香港身份证分支
**描述**：测试 `ssicType` 为 "3" (香港身份证) 的认证结果。
**准备条件（GIVEN）**：获取开关表里接口名称为UniformTeller.qryTellerInfo的那条信息，将status字段设置成1-启用。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "3",
  "ssicId": "B234567(8)",
  "username": "刘八",
  "email": "liuba@example.com",
  "phone": "+8613812345678",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4",
    "timestamp": "2025-07-15 11:03:46"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "data": {
    "field1": "香港身份证用户-B234567(8)",
    "field2": {
      "sub_field1": 0,
      "sub_field2": false
    },
    "field3": "服务站点: 香港支行"
  },
  "return_code": "0",
  "return_msg": "请求处理成功"
}
```
###  失败场景：缺少必填参数 `ssicType`
**描述**：验证当缺少必填参数 `ssicType` 时，接口能够返回错误信息。
**请求URL**：`http://192.168.8.123:8080/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicId": "392201198902121234",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
    "data": null,
  "return_code": "1003",
  "return_msg": "认证类型不能为空"
}
```

###  失败场景：缺少必填参数 `ssicId`
**描述**：验证当缺少必填参数 `ssicId` 时，接口能够返回错误信息。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "1",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "data": null,
  "return_code": "1003",
  "return_msg": "认证号不能为空"
}
```

###  失败场景：缺少必填参数 `biz_content.serviceName`
**描述**：验证当缺少必填参数 `biz_content.serviceName` 时，接口能够返回错误信息。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "1",
  "ssicId": "392201198902121234",
  "biz_content": {
    "randomKey": "smxxxxxxxxsm4"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "data": null,
  "return_code": "1003",
  "return_msg": "站点名称不能为空"
}
```

###  失败场景：缺少必填参数 `biz_content.randomKey`
**描述**：验证当缺少必填参数 `biz_content.randomKey` 时，接口能够返回错误信息。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "1",
  "ssicId": "392201198902121234",
  "biz_content": {
    "serviceName": "AAM"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "data": null,
  "return_code": "1003",
  "return_msg": "随机密钥不能为空"
}
```

###  失败场景：`X-Request-App` 请求头缺失
**描述**：验证当请求头 `X-Request-App` 缺失时，接口能够返回错误信息。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "1",
  "ssicId": "392201198902121234",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "data": null,
  "return_code": "1003",
  "return_msg": "请求头 X-Request-App 不能为空"
}
```

###  失败场景：`X-Request-Id` 请求头缺失
**描述**：验证当请求头 `X-Request-Id` 缺失时，接口能够返回错误信息。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
```
**请求体**：
```json
{
  "ssicType": "1",
  "ssicId": "392201198902121234",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "data": null,
  "return_code": "1003",
  "return_msg": "请求头 X-Request-Id 不能为空"
}
```



###  失败场景：`ssicType` 非法取值
**描述**：验证当 `ssicType` 传入非法值时，接口能够返回“无效的认证类型”错误。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "99", 
  "ssicId": "392201198902121234",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "return_code": "1001",
  "return_msg": "无效的认证类型",
  "data": null
}
```

###  失败场景：用户未激活
**描述**：验证当用户存在但状态为未激活（`status` 为 '0'）时，接口能够返回“用户未激活”错误。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "2",
  "ssicId": "110101199002022345",
  "username": "赵六",
  "email": "zhaoliu@example.com",
  "phone": "13800138004",
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4",
    "timestamp": "2019-07-01 09:01:01"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "data": null,
  "return_code": "1004",
  "return_msg": "用户未激活"
}
```

###  失败场景：身份验证失败 (用户不存在)
**描述**：验证当 `ssicId` 对应的用户不存在时，接口能够返回身份验证失败的错误信息。
**请求URL**：`/api/aam/uniformteller/qrytellerInfo/V1`
**请求方法**：POST
**请求头**：
```
X-Request-App: F-CCPS
X-Request-Id: 2010434
```
**请求体**：
```json
{
  "ssicType": "1",
  "ssicId": "NON_EXISTENT_SSIC_ID", 
  "biz_content": {
    "serviceName": "AAM",
    "randomKey": "smxxxxxxxxsm4"
  }
}
```
**预期响应状态码**：200 OK
**预期响应体**：
```json
{
  "return_code": "1001",
  "return_msg": "校验身份信息失败",
  "data": null
}
```

