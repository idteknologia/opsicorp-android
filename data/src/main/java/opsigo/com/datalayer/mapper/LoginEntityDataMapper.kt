package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.LoginEntity
import opsigo.com.domainlayer.model.signin.DataLoginModel

class LoginEntityDataMapper{

    fun transform(loginEntity: LoginEntity): DataLoginModel {
        val user = DataLoginModel()

        user.token = loginEntity.accessToken
        user.userName = loginEntity.userName
        user.employId = loginEntity.employeeId

        return user
    }
}
