package com.example.location.kotlin.property
import com.google.gson.annotations.SerializedName

data class PropertyClass (

    @SerializedName("status"   ) var status   : String?   = null,
    @SerializedName("response" ) var response : Response? = Response()

)

data class Response (

    @SerializedName("id"                  ) var id                : String?                     = null,
    @SerializedName("user_uuid"           ) var userUuid          : String?                     = null,
    @SerializedName("status"              ) var status            : String?                     = null,
    @SerializedName("draft_step"          ) var draftStep         : String?                     = null,
    @SerializedName("wdraft_step"         ) var wdraftStep        : String?                     = null,
    @SerializedName("verified_status"     ) var verifiedStatus    : String?                     = null,
    @SerializedName("d3_tour_status"      ) var d3TourStatus      : String?                     = null,
    @SerializedName("d3_slot_date"        ) var d3SlotDate        : String?                     = null,
    @SerializedName("d3_slot_time"        ) var d3SlotTime        : String?                     = null,
    @SerializedName("d3_dt"               ) var d3Dt              : String?                     = null,
    @SerializedName("d3_iframe_url"       ) var d3IframeUrl       : String?                     = null,
    @SerializedName("advertise_plan"      ) var advertisePlan     : String?                     = null,
    @SerializedName("plan_id"             ) var planId            : String?                     = null,
    @SerializedName("plan_end_dt"         ) var planEndDt         : String?                     = null,
    @SerializedName("black_plan"          ) var blackPlan         : String?                     = null,
    @SerializedName("title"               ) var title             : String?                     = null,
    @SerializedName("location_id"         ) var locationId        : String?                     = null,
    @SerializedName("address"             ) var address           : String?                     = null,
    @SerializedName("property_lat"        ) var propertyLat       : String?                     = null,
    @SerializedName("property_lon"        ) var propertyLon       : String?                     = null,
    @SerializedName("home_type"           ) var homeType          : String?                     = null,
    @SerializedName("bedrooms"            ) var bedrooms          : String?                     = null,
    @SerializedName("master_bedrooms"     ) var masterBedrooms    : String?                     = null,
    @SerializedName("bathrooms"           ) var bathrooms         : String?                     = null,
    @SerializedName("floors"              ) var floors            : String?                     = null,
    @SerializedName("car_parkings"        ) var carParkings       : String?                     = null,
    @SerializedName("year_of_contruct"    ) var yearOfContruct    : String?                     = null,
    @SerializedName("price"               ) var price             : String?                     = null,
    @SerializedName("has_swimming_pool"   ) var hasSwimmingPool   : String?                     = null,
    @SerializedName("has_furnished"       ) var hasFurnished      : String?                     = null,
    @SerializedName("has_basement"        ) var hasBasement       : String?                     = null,
    @SerializedName("has_maid_room"       ) var hasMaidRoom       : String?                     = null,
    @SerializedName("has_driver_room"     ) var hasDriverRoom     : String?                     = null,
    @SerializedName("has_balcony"         ) var hasBalcony        : String?                     = null,
    @SerializedName("description"         ) var description       : String?                     = null,
    @SerializedName("photos"              ) var photos            : ArrayList<Photos>           = arrayListOf(),
    @SerializedName("cover_photo"         ) var coverPhoto        : ArrayList<String>           = arrayListOf(),
    @SerializedName("bedroom_photo"       ) var bedroomPhoto      : ArrayList<BedroomPhoto>     = arrayListOf(),
    @SerializedName("living_room_photo"   ) var livingRoomPhoto   : ArrayList<LivingRoomPhoto>  = arrayListOf(),
    @SerializedName("other_photo"         ) var otherPhoto        : ArrayList<OtherPhoto>       = arrayListOf(),
    @SerializedName("property_features"   ) var propertyFeatures  : ArrayList<PropertyFeatures> = arrayListOf(),
    @SerializedName("pid"                 ) var pid               : String?                     = null,
    @SerializedName("code"                ) var code              : String?                     = null,
    @SerializedName("is_contacted"        ) var isContacted       : String?                     = null,
    @SerializedName("is_favourite"        ) var isFavourite       : String?                     = null,
    @SerializedName("is_tour_enabled"     ) var isTourEnabled     : String?                     = null,
    @SerializedName("tour_booking_status" ) var tourBookingStatus : String?                     = null,
    @SerializedName("is_own_property"     ) var isOwnProperty     : String?                     = null,
    @SerializedName("location_name"       ) var locationName      : String?                     = null,
    @SerializedName("property_link"       ) var propertyLink      : String?                     = null,
    @SerializedName("owner_info"          ) var ownerInfo         : OwnerInfo?                  = OwnerInfo(),
    @SerializedName("similar_property"    ) var similarProperty   : ArrayList<SimilarProperty>  = arrayListOf()

)

data class SimilarProperty (

    @SerializedName("id"               ) var id             : String? = null,
    @SerializedName("user_uuid"        ) var userUuid       : String? = null,
    @SerializedName("status"           ) var status         : String? = null,
    @SerializedName("d3_tour_status"   ) var d3TourStatus   : String? = null,
    @SerializedName("d3_iframe_url"    ) var d3IframeUrl    : String? = null,
    @SerializedName("home_type"        ) var homeType       : String? = null,
    @SerializedName("bedrooms"         ) var bedrooms       : String? = null,
    @SerializedName("bathrooms"        ) var bathrooms      : String? = null,
    @SerializedName("floors"           ) var floors         : String? = null,
    @SerializedName("car_parkings"     ) var carParkings    : String? = null,
    @SerializedName("year_of_contruct" ) var yearOfContruct : String? = null,
    @SerializedName("price"            ) var price          : String? = null,
    @SerializedName("black_plan"       ) var blackPlan      : String? = null,
    @SerializedName("code"             ) var code           : String? = null,
    @SerializedName("photo"            ) var photo          : String? = null,
    @SerializedName("location_name"    ) var locationName   : String? = null,
    @SerializedName("is_favourite"     ) var isFavourite    : String? = null,
    @SerializedName("property_link"    ) var propertyLink   : String? = null

)


data class OwnerInfo (

    @SerializedName("name"        ) var name       : String? = null,
    @SerializedName("phone"       ) var phone      : String? = null,
    @SerializedName("email"       ) var email      : String? = null,
    @SerializedName("profile_img" ) var profileImg : String? = null

)


data class PropertyFeatures (

    @SerializedName("title" ) var title : String? = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("value" ) var value : String? = null
)

data class OtherPhoto (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("path" ) var path : String? = null

)


data class LivingRoomPhoto (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("path" ) var path : String? = null

)

data class BedroomPhoto (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("path" ) var path : String? = null

)

data class Photos (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("path" ) var path : String? = null

)