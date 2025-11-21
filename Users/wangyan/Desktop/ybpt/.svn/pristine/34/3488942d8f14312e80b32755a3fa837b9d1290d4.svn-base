
const { handleMockArray } = require('./utils')

const mocks = []
const mockArray = handleMockArray()
mockArray.forEach((item) => {
  const obj = require(item)
  mocks.push(...obj)
})
module.exports = {
  mocks,
}
