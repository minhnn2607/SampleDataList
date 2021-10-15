package vn.nms.sample.domain.repo

interface SharePrefRepo {
    fun saveLoginFlag()
    fun getLoginFlag(): Boolean
    fun clearUserData()
}