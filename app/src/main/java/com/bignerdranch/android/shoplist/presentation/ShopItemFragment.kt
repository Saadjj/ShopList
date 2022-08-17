package com.bignerdranch.android.shoplist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.shoplist.R
import com.bignerdranch.android.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //проверка
        parseParams()
    }

    //из макета создаем вью, похожий метод был у Recicler view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_shop_item, container, false
        )
    }

    //вьюшка уже точно создана и  сней можно спокойно работать
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //инициализация вьюмодели
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        //инициализация вьюшек
        initViews(view)
        addChangeTextListeners()

        //настройка экрана, если до этого все ок
        launchRightMode()
        observeViewModel()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }


    private fun addChangeTextListeners() {
        //при изменении текста убираем исключение от пользователя
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun observeViewModel() {
        //подписываемся на объект ошибки числа
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        //подписываемся на объект ошибки ввода имени
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        //закрываем экран
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            //метод эквивалентен нажатию кнопки назад
            activity?.onBackPressed()
        }
    }

    //подписаться на нужын еобъеты лайвдаты
    private fun launchEditMode() {
        //получение собственно итема
        viewModel.getShopItem(shopItemId)
        //а теперь подписаны на него, и устанавливаем нужные значения
        viewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener() {
            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        //мощь
        buttonSave.setOnClickListener() {
            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }


    /**
     * проверяем входящие параметры интента
     */
    //чтобы показать франмент интенты не используются
    private fun parseParams() {
        //здесь изпольуется метод, возвращающий ненулабельный тип, если приложение упадет то ок
        //получаем переданные аргументы
        val args=requireArguments()
        //проверки на всяко разно
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Param screen mode is unknown $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)){
                throw RuntimeException("Param EXTRA_SHOP_ITEM_ID is absent")
            }
                shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)


        }
    }




    /**
     * инициализация вьюшек
     */
    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)

    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

        /**
         * фабричные статические методы для создания новых объектов c MODE_ADD
         */
        fun newInstanceAddItem(): ShopItemFragment {
            //не пугаться Bundle(), это мапа
            val args = Bundle().apply {
                putString(SCREEN_MODE, MODE_ADD)
            }
//            args.putString(SCREEN_MODE, MODE_ADD)
            val fragment = ShopItemFragment().apply {
                arguments = args
            }
//            fragment.arguments = args
            return fragment
        }

        /**
         * фабричные статические методы для создания новых объектов c MODE_EDIT
         */
        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            val args = Bundle().apply {
                putString(SCREEN_MODE, MODE_EDIT)
                putInt(SHOP_ITEM_ID,shopItemId)
            }
            val fragment=ShopItemFragment().apply{
                arguments=args
            }
            return fragment
        }


    }
}