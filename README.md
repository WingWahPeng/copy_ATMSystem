# copy_ATMSystem
这是模仿银行ATM的一个程序系统

正常的状态为1，冻结为2,挂失为3，注销为4

银行卡被冻结可以查询余额，也可以进行资金转入的，但不允许转出（可以接受汇款与存款）。可以修改密码。可以在ATM机上登录。
挂失时可以存入，不能支取，不能在ATM机上登录，插入后会显示为无效卡。
被注销的银行卡在ATM机上是不能使用的，插入之后会显示无效卡。
银行卡简单为分为两种，本行卡与其它银行卡。

1、完善注释
2、改正登录面板账号与密码为空时的响应错误，密码为空时会计算密码输入错误次数的响应错误。（已完成）
3、增加一天结束，密码可输入错误次数更正回零，复原当日可转账金额。（待完成）--通过数据库的作业来完成。
4、修正银行卡被冻结后在查询界面还可以取款与转帐的操作。（已完成）
5、增加银行卡的类别，划分为信用卡与借记卡。明确信用卡与借记卡的区别（可选）
6、增加密码一天输入三次错误导致冻结（密码冻结，应增加一个字段来代表密码冻结才可完成），24小时后解冻。每天的24点自动复原。（待完成）
7、增加其他银行卡的数据表。（可选）
8、修正其他银行的卡可以在本行的ATM机进行转账（已完成）
