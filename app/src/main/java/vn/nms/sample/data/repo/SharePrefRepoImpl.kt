package vn.nms.sample.data.repo

import vn.nms.sample.data.manager.SharedPrefsManager
import vn.nms.sample.domain.config.KeyConfig
import vn.nms.sample.domain.repo.SharePrefRepo
import javax.inject.Inject

class SharePrefRepoImpl @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
) : SharePrefRepo {
    override fun saveLoginFlag() {
        sharedPrefsManager.put(KeyConfig.LOGIN_FLAG, true)
    }

    override fun getLoginFlag(): Boolean =
        sharedPrefsManager[KeyConfig.LOGIN_FLAG, Boolean::class.java] ?: false

    override fun clearUserData() {
        sharedPrefsManager.clearDataByKey(KeyConfig.LOGIN_FLAG)
    }

}