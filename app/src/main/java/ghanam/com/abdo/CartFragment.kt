package ghanam.com.abdo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ghanam.com.abdo.adapters.CartItemAdapter
import ghanam.com.abdo.databinding.FragmentCartBinding
import ghanam.com.abdo.singletons.DecimalFormatter
import ghanam.com.abdo.singletons.VirtualDB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigDecimal
import java.math.BigDecimal.valueOf
import java.math.RoundingMode
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    private lateinit var itemsAdapter: CartItemAdapter
    //private var total: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var total=VirtualDB.calculateTotal()


        binding= FragmentCartBinding.inflate(inflater, container, false)
        binding.apply {
            deliveryFeeText.text=DecimalFormatter.format(VirtualDB.deliveryFee)
            subtotalText.text=DecimalFormatter.format(total)
            totalText.text=DecimalFormatter.format(total+VirtualDB.deliveryFee)
            itemsAdapter = CartItemAdapter(VirtualDB.items,binding)
            itemsCartRecycler.adapter=itemsAdapter
            itemsCartRecycler.layoutManager = LinearLayoutManager(context)
            continueShoppingButton.setOnClickListener {
                continueShopping(it)
            }
            checkoutButton.setOnClickListener {
                checkOut(it)
            }
        }


        return binding.root
    }



    private fun checkOut(it: View?) {
        if (it != null) {
            if (VirtualDB.getItemsCount()>0) {

                val currentUser = FirebaseAuth.getInstance().currentUser
                val ordersCollection = FirebaseFirestore.getInstance().collection("orders")
                val newOrder = HashMap<String, Any>()
                newOrder["userId"] = currentUser?.uid ?: ""
                newOrder["orderTotal"] = VirtualDB.calculateTotal().plus(5.0f).roundToLong()
                ordersCollection.add(newOrder)
                    .addOnSuccessListener { documentReference ->
                        VirtualDB.addPrevOrder()
                        Navigation.findNavController(it)
                            .navigate(R.id.action_cartFragment_to_orderConfirmedFragment)
                        VirtualDB.clearCart()
                        Toast.makeText(context, "successful order", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->

                        Toast.makeText(
                            context,
                            "Error placing order: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else
                Toast.makeText(context,"add something to cart first!",Toast.LENGTH_SHORT).show()

        }
    }

    private fun continueShopping(it: View?) {
        if (it != null) {
            Navigation.findNavController(it).navigate(R.id.action_cartFragment_to_homeFragment)
        }
    }


}