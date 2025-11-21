import store from '../store'

/**
 *  创建组件
 * @param Component 组件
 * @param props 参数
 * @param callback 回调
 * @returns {*}
 */
export const createDialog = (Component, props, callback) => {
  const container = document.createElement('div')

  let instance = new Component({
    data: {
      ...props,
      callback: (...args) => {
        if (args.length === 2) {
          // 异步执行
          if (args[1] instanceof Function) {
            callback && callback(...args)
          } else {
            // 延迟执行，需要在窗口动画结束之后进行组件销毁
            setTimeout(() => {
              if (instance) {
                instance.$destroy()
                instance = undefined
              }
            }, 500)
            callback && callback(...args)
          }
        } else {
          setTimeout(() => {
            if (instance) {
              instance.$destroy()
              instance = undefined
            }
          }, 500)
        }
      },
    },
    store,
  })

  document.body.appendChild(container)
  instance.$mount(container)
  return instance
}
