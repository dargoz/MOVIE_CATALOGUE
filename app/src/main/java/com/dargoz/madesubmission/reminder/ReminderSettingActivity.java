package com.dargoz.madesubmission.reminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dargoz.madesubmission.Constant;
import com.dargoz.madesubmission.R;

public class ReminderSettingActivity extends AppCompatActivity
        implements Switch.OnCheckedChangeListener {
    private AlarmReceiver alarmReceiver;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_setting);
        alarmReceiver = new AlarmReceiver();
        sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

        Switch dailySwitch = findViewById(R.id.daily_setting_switch);
        Switch releaseSwitch = findViewById(R.id.release_setting_switch);

        dailySwitch.setChecked(
                sharedPreferences.getBoolean(Constant.save_daily_reminder_value, false)
        );
        releaseSwitch.setChecked(
                sharedPreferences.getBoolean(Constant.save_release_reminder_value, false)
        );

        dailySwitch.setOnCheckedChangeListener(this);
        releaseSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (compoundButton.getId()){
            case R.id.daily_setting_switch:
                editor.putBoolean(Constant.save_daily_reminder_value, checked);
                if(checked)
                    alarmReceiver.setRepeatingAlarm(
                            this, AlarmReceiver.TYPE_DAILY_REMINDER, "07:00:00"
                    );
                else
                    alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_DAILY_REMINDER);
                break;
            case R.id.release_setting_switch:
                editor.putBoolean(Constant.save_release_reminder_value, checked);
                if (checked)
                    alarmReceiver.setRepeatingAlarm(
                            this, AlarmReceiver.TYPE_RELEASE_TODAY, "08:00:00"
                    );
                else
                    alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_RELEASE_TODAY);
                break;
                default:
                    break;
        }
        editor.apply();
    }
}
