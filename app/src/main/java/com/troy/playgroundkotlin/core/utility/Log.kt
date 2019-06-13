package com.troy.playgroundkotlin.core.utility

/**
 * A convenience logger intend to replace android.util.Log
 * To use this class. No tag need to be defined.
 * All the available methods are static and please refer to andorid.util.log,
 * just ignore the first argument tag.
 * for instance : `Log.i("hello"); // in class LaunchActivity, as default output "tmms-LaunchActivity hello"`
 * @author Zhentao Huang
 */
object Log {

    val caller: String
        get() = tag

    private var sMode = Mode.AppNameWithMethod
    private var sEnabled = true
    private var sAppName = "Soocii"

    private val tag: String
        get() {
            when (sMode) {
                Log.Mode.AppNameWithClass -> {
                    val st = Thread.currentThread().stackTrace
                    if (st != null && st.size >= 4) {
                        val caller = getCallerInfo2(st)
                        return sAppName + "-" + caller[0]
                    }
                }
                Log.Mode.AppNameWithMethod -> {
                    val st = Thread.currentThread().stackTrace
                    if (st != null && st.size >= 4) {
                        val caller = getCallerInfo2(st)
                        return sAppName + "-" + caller[0] + ":" + caller[1]
                    }
                }
            }
            return sAppName
        }

    /**
     * Defines 3 kinds of tag mode
     * AppName -- The tag would be only APP's name
     * AppNameWithClass -- The tag would &lt;appname&gt;-&lt;classname&gt;
     * AppNameWithMethod -- The tag would be &lt;appname&gt;-&lt;classname&gt;:&lt;methodname&gt;
     *
     */
    enum class Mode {
        AppName, AppNameWithClass, AppNameWithMethod
    }

    /**
     * Enable the logger or not.
     * @param enabled
     */
    fun setEnable(enabled: Boolean) {
        sEnabled = enabled
    }

    /**
     * Setup logger tag mode
     * @param mode @see Mode
     */
    fun setMode(mode: Mode) {
        sMode = mode
    }

    /**
     * Setup application name
     * @param name
     */
    fun setAppName(name: String) {
        sAppName = name
    }

    fun i(message: String) {
        //if (sEnabled)
        android.util.Log.i(tag, message)
    }

    fun i(message: String, e: Throwable) {
        //if (sEnabled)
        android.util.Log.i(tag, message, e)
    }

    fun e(message: String) {
        //if (sEnabled)
        android.util.Log.e(tag, message)
    }

    fun e(message: String, e: Throwable) {
        //if (sEnabled)
        android.util.Log.e(tag, message, e)
    }

    fun d(message: String) {
        if (sEnabled)
            android.util.Log.d(tag, message)
    }

    fun d(message: String, e: Throwable) {
        if (sEnabled)
            android.util.Log.d(tag, message, e)
    }

    fun v(message: String) {
        if (sEnabled)
            android.util.Log.v(tag, message)
    }

    fun v(message: String, e: Throwable) {
        if (sEnabled)
            android.util.Log.v(tag, message, e)
    }

    fun w(message: String) {
        if (sEnabled)
            android.util.Log.w(tag, message)
    }

    fun w(message: String, e: Throwable) {
        if (sEnabled)
            android.util.Log.w(tag, message, e)
    }

    private fun getCallerInfo(st: Array<StackTraceElement>): Array<String> {
        var findSelf = false
        val info = arrayOf("Unknown", "Unknown")

        for (e in st) {
            val name = e.className
            if (!findSelf) {
                if (name == Log::class.java.name) {
                    findSelf = true
                }
            } else {
                if (name != Log::class.java.name) {
                    val s = name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    info[0] = s[s.size - 1]
                    info[1] = e.methodName
                    break
                }
            }
        }

        return info
    }

    private fun getCallerInfo2(st: Array<StackTraceElement>): Array<String> {
        var findSelf = false
        val info = arrayOf("Unknown", "Unknown")

        for (e in st) {
            val name = e.className
            if (!findSelf) {
                if (name == Log::class.java.name) {
                    findSelf = true
                }
            } else {
                if (name != Log::class.java.name) {
                    val s = name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    info[0] = s[s.size - 1]
                    info[1] = e.methodName
                    break
                }
            }
        }

        return info
    }
}
