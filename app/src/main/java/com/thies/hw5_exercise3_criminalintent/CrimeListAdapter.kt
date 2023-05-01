package com.thies.hw5_exercise3_criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.thies.hw5_exercise3_criminalintent.Const.NO_POLICE
import com.thies.hw5_exercise3_criminalintent.Const.REQUIRES_POLICE
import com.thies.hw5_exercise3_criminalintent.databinding.ListItemCrimeBinding
import com.thies.hw5_exercise3_criminalintent.databinding.ListItemCrimeSeriousBinding
import java.text.DateFormat


// Used this link to help me work through part B of exercise 2:
// https://www.section.io/engineering-education/implementing-multiple-viewholders-in-android-using-kotlin/

class CrimeHolder(private val binding: ListItemCrimeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        //binding.crimeDate.text = crime.date.toString()
        // used the following link to help format date for part b of exercise 3:
        // https://developer.android.com/reference/java/text/DateFormat
        binding.crimeDate.text = DateFormat.getDateInstance().format(crime.date)

        // detecting clicks in CrimeHolder
        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.crimeSolved.visibility = if(crime.isSolved) {
            View.VISIBLE
        }else{
            View.GONE
        }
    }
}

class SeriousCrimeHolder(private val binding: ListItemCrimeSeriousBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        //binding.crimeDate.text = crime.date.toString()
        binding.crimeDate.text = DateFormat.getDateInstance().format(crime.date)
        binding.crimeRequiresPolice

        // detecting clicks in CrimeHolder
        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

// sits in between RecyclerView and data set; RecyclerView does not know anything about Crimes
class CrimeListAdapter(private val crimes: List<Crime>) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (crimes[position].requiresPolice == RequiresPolice.TRUE) REQUIRES_POLICE else NO_POLICE
    }

    // creates a binding to display, wraps it in view holder, and returns the result in a new instance of CrimeHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == NO_POLICE) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
            return CrimeHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemCrimeSeriousBinding.inflate(inflater, parent, false)
            return SeriousCrimeHolder(binding)
        }


    }
    // populates holder with crime from given position
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == NO_POLICE) {
            (holder as CrimeHolder).bind(crimes[position])
        } else {
            (holder as SeriousCrimeHolder).bind(crimes[position])
        }
        /*holder.apply {
            // set text from given crime in corresponding text views
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()
        }*/
        //holder.bind(crime)
    }

    override fun getItemCount() = crimes.size
}

private object Const {
    const val NO_POLICE = 0
    const val REQUIRES_POLICE = 1
}