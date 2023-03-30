package dev.topping.android

import android.widget.LGRecyclerViewAdapter
import android.widget.LGView
import dev.topping.android.backend.LuaClass
import dev.topping.android.luagui.LuaContext

/**
 * Interface that you can override in kotlin to approach LGRecyclerViewAdapter more objective way
 */
@LuaClass(className = "ILGRecyclerViewAdapter", isKotlin = true)
class ILGRecyclerViewAdapter {
    /**
     * (Ignore)
     */
    lateinit var ltGetItemCount: LuaTranslator

    /**
     * (Ignore)
     */
    lateinit var ltOnCreateViewHolder: LuaTranslator

    /**
     * (Ignore)
     */
    lateinit var ltOnBindViewHolder: LuaTranslator

    /**
     * (Ignore)
     */
    lateinit var ltGetItemViewType: LuaTranslator

    /**
     * onCreateViewHolder call
     * @param parent
     * @param viewType
     * @param context
     */
    fun onCreateViewHolder(parent: LGView, viewType: Int, context: LuaContext) {}

    /**
     * onBindViewHolder call
     * @param view
     * @param position
     */
    fun onBindViewHolder(view: LGView, position: Int) {}

    /**
     * getItemViewType call
     * @param position
     * @return type
     */
    fun getItemViewType(position: Int): Int {
        return 0
    }

    /**
     * Gets the item count
     * @return count
     */
    fun getItemCount(): Int {
        return 0
    }

    /**
     * Access to adapter
     * @return LGRecyclerViewAdapter
     */
    fun getAdapter(): LGRecyclerViewAdapter {
        return LGRecyclerViewAdapter(LuaContext(), "")
    }
}