package opsigo.com.datalayer.model.listtripplan

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.PaymentsItem
import opsigo.com.datalayer.model.RemarksItem
import opsigo.com.datalayer.model.approval.hotel.GuestItem

@Generated("com.robohorse.robopojogenerator")
data class TripHotelsItem(

        @field:SerializedName("TripPlanId")
	val tripPlanId: String? = "",

        @field:SerializedName("Email")
	val email: String? = "",

        @field:SerializedName("RemarkHotel")
	val remarkHotel: String? = "",

        @field:SerializedName("Address")
	val address: String? = "",

        @field:SerializedName("IsForwardedBooking")
	val isForwardedBooking: String? = "",

        @field:SerializedName("IsSync")
	val isSync: String? = "",

        @field:SerializedName("Payments")
	val payments: List<PaymentsItem> = ArrayList(),

        @field:SerializedName("Hash")
	val hash: String? = "",

        @field:SerializedName("Image")
	val image: String? = "",

        @field:SerializedName("CancellationPolicies")
	val cancellationPolicies: List<String> = ArrayList<String>(),

        @field:SerializedName("FormBookingUrl")
	val formBookingUrl: String? = "",

        @field:SerializedName("CancelReff")
	val cancelReff: String? = "",

        @field:SerializedName("Name")
	val name: String? = "",

        @field:SerializedName("ActionDate")
	val actionDate: String? = "",

        @field:SerializedName("IsFullCharge")
	val isFullCharge: String? = "",

        @field:SerializedName("Checkout")
	val checkout: String? = "",

        @field:SerializedName("Remarks")
	val remarks: List<RemarksItem> = ArrayList(),

        @field:SerializedName("ContractNo")
	val contractNo: String? = "",

        @field:SerializedName("RoomSelector")
	val roomSelector: String? = "",

        @field:SerializedName("ResponseBookUrl")
	val responseBookUrl: String? = "",

        @field:SerializedName("RoomService")
	val roomService: String? = "",

        @field:SerializedName("CauseViolatedRules")
	val causeViolatedRules: String? = "",

        @field:SerializedName("CorrelationId")
	val correlationId: String? = "",

        @field:SerializedName("EmpId")
	val empId: String? = "",

        @field:SerializedName("IsSentEmail")
	val isSentEmail: String? = "",

        @field:SerializedName("Status")
	val status: String? = "",

        @field:SerializedName("CheckinFormatString")
	val checkinFormatString: String? = "",

        @field:SerializedName("ConfirmationId")
	val confirmationId: String? = "",

        @field:SerializedName("IsEmailSent")
	val isEmailSent: String? = "",

        @field:SerializedName("TicketedDate")
	val ticketedDate: String? = "",

        @field:SerializedName("CitiNameApi")
	val citiNameApi: String? = "",

        @field:SerializedName("Guest")
	val guest: List<GuestItem> = ArrayList(),

        @field:SerializedName("ConfirmationUrl")
	val confirmationUrl: String? = "",

        @field:SerializedName("Duration")
	val duration: String? = "",

        @field:SerializedName("StatusName")
	val statusName: String? = "",

        @field:SerializedName("City")
	val city: String? = "",

        @field:SerializedName("CityName")
	val cityName: String? = "",

        @field:SerializedName("EmailFD")
	val emailFD: String? = "",

        @field:SerializedName("DiscountAmount")
	val discountAmount: String? = "",

        @field:SerializedName("CheckinView")
	val checkinView: String? = "",

        @field:SerializedName("ServiceFee")
	val serviceFee: String? = "",

        @field:SerializedName("Area")
	val area: String? = "",

        @field:SerializedName("TripCode")
	val tripCode: String? = "",

        @field:SerializedName("TimeLimitView")
	val timeLimitView: String? = "",

        @field:SerializedName("IsFullDeduct")
	val isFullDeduct: String? = "",

        @field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: String? = "",

        @field:SerializedName("DiscountType")
	val discountType: String? = "",

        @field:SerializedName("PlafondAmount")
	val plafondAmount: String? = "",

        @field:SerializedName("TravelAgent")
	val travelAgent: String? = "",

        @field:SerializedName("CreatedDate")
	val createdDate: String? = "",

        @field:SerializedName("Country")
	val country: String? = "",

        @field:SerializedName("Id")
	val id: String? = "",

        @field:SerializedName("BookingNumber")
	val bookingNumber: String? = "",

        @field:SerializedName("BookingCode")
	val bookingCode: String? = "",

        @field:SerializedName("Message")
	val message: String? = "",

        @field:SerializedName("BookedDate")
	val bookedDate: String? = "",

        @field:SerializedName("Rating")
	val rating: String? = "",

        @field:SerializedName("IsTourism")
	val isTourism: String? = "",

        @field:SerializedName("TourismTax")
	val tourismTax: String? = "",

        @field:SerializedName("HotelType")
	val hotelType: String? = "",

        @field:SerializedName("CancellationPoliciesView")
	val cancellationPoliciesView: List<String> = ArrayList<String>(),

        @field:SerializedName("CostId")
	val costId: String? = "",

        @field:SerializedName("CheckoutView")
	val checkoutView: String? = "",

        @field:SerializedName("EmailFO")
	val emailFO: String? = "",

        @field:SerializedName("RoomType")
	val roomType: String? = "",

        @field:SerializedName("PnrId")
	val pnrId: String? = "",

        @field:SerializedName("LinkReference")
	val linkReference: String? = "",

        @field:SerializedName("Phone")
	val phone: String? = "",

        @field:SerializedName("IsGuarantedBooking")
	val isGuarantedBooking: String? = "",

        @field:SerializedName("RemarkReject")
	val remarkReject: String? = "",

        @field:SerializedName("CancellationApi")
	val cancellationApi: String? = "",

        @field:SerializedName("TimeLimit")
	val timeLimit: String? = "",

        @field:SerializedName("MapUri")
	val mapUri: String? = "",

        @field:SerializedName("CheckOutFormatString")
	val checkOutFormatString: String? = "",

        @field:SerializedName("ReffId")
	val reffId: String? = "",

        @field:SerializedName("Amount")
	val amount: String? = "",

        @field:SerializedName("TotalAmountApi")
	val totalAmountApi: String? = "",

        @field:SerializedName("TotalAmount")
	val totalAmount: String? = "",

        @field:SerializedName("IsWithBreakfast")
	val isWithBreakfast: String? = "",

        @field:SerializedName("BookingCodeNew")
	val bookingCodeNew: String? = "",

        @field:SerializedName("Facilities")
	val facilities: String? = "",

        @field:SerializedName("Rooms")
	val rooms: String? = "",

//		@field:SerializedName("Contact")
//	val contact: Contact = Contact(),

        @field:SerializedName("HotelKey")
	val hotelKey: String? = "",

        @field:SerializedName("AmountWithSf")
	val amountWithSf: String? = "",

        @field:SerializedName("CauseViolatedRulesParagraf")
	val causeViolatedRulesParagraf: String? = "",

        @field:SerializedName("RoomCategory")
	val roomCategory: String? = "",

        @field:SerializedName("TimeLimitTripPlan")
	val timeLimitTripPlan: String? = "",

        @field:SerializedName("DiscountPercentage")
	val discountPercentage: String? = "",

        @field:SerializedName("TimeLimitFormatString")
	val timeLimitFormatString: String? = "",

        @field:SerializedName("TripItemId")
	val tripItemId: String? = "",

        @field:SerializedName("Checkin")
	val checkin: String? = "",

        @field:SerializedName("AmountPerNight")
	val amountPerNight: String? = ""
)