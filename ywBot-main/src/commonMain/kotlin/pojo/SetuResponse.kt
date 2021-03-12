package com.elouyi.pojo

data class SetuResponse(
    var code: Int = 0,
    var msg: String = "",
    var quota: Int = 0,
    var count: Int = 1,
    var data: List<Data> = mutableListOf()) {


    data class Data(
        var pid: Int = 0,
        var p: Int = 0,
        var uid: Int = 0,
        var title: String = "",
        var author: String = "",
        var url: String = "",
        var r18: Boolean = false,
        var width: Int = 0,
        var height: Int = 0,
        var tags: MutableList<String> = mutableListOf()
    )

}
