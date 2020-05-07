package AbstractCLI.Commands._old_tmp.Handling.Std;

import AbstractCLI.Commands._old_tmp.Handling.NoArgHandler;

/**
 * Простой обработчик, который не делает ничего кроме возврата значения
 */
public class ReturnHandler implements NoArgHandler {
    int ret = 0;

    public ReturnHandler() { }
    public ReturnHandler(int ret) { this.ret = ret; }

    @Override
    public int handle() { return ret; }
}
