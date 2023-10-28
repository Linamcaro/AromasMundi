package com.example.aromasmundi.ui.fragments.feed.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.aromasmundi.R
import com.example.aromasmundi.databinding.RecipesBottomSheetBinding
import com.example.aromasmundi.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.aromasmundi.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.aromasmundi.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var bottomSheetBinding: RecipesBottomSheetBinding
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        bottomSheetBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.recipes_bottom_sheet,
            container,
            false
        )

        //get the data from the dataStore when there is new data and update the chips with the selected data
        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            mealTypeId = value.selectedMealTypeId
            dietTypeId = value.selectedDietTypeId

            updateChip(mealTypeId, bottomSheetBinding.mealTypeChipGroup)
            updateChip(dietTypeId, bottomSheetBinding.dietTypeChipGroup)
        }

        //store the information of the selected meal type
        bottomSheetBinding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->

            val chip = group.findViewById<Chip>(selectedChipId[0])
            mealTypeChip = chip.text.toString().lowercase()
            mealTypeId = chip.id
        }

        // //store the information of the selected diet type
        bottomSheetBinding.dietTypeChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->

            val chip = group.findViewById<Chip>(selectedChipId[0])
            dietTypeChip = chip.text.toString().lowercase()
            dietTypeId = chip.id
        }

        //save the current selected meal and diet type preference
       bottomSheetBinding.applyButton.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeId,
                dietTypeChip,
                dietTypeId
            )

           val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToFeedFragment(true)
           findNavController().navigate(action)
        }

        return bottomSheetBinding.root
    }

    //update the chips with the new selected data
    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

}