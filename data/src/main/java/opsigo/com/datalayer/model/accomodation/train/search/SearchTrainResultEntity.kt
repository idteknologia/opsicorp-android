package opsigo.com.datalayer.model.accomodation.train.search

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SearchTrainResultEntity(

        @field:SerializedName("result")
	val result: ResultSearchTrainEntity = ResultSearchTrainEntity(),

        @field:SerializedName("errorMessage")
	val errorMessage: String = ""
)