def maxSubArray(array): # O(n^2)
    maxSum = array[0]
    # for loops going from 0 to end of array
    # and another one
    # double nested for loops so O(n^2)
    # this essentially gets all the possible subarrays
    # and checks each time if it is greater than the overall maxSum
    for i in range(len(array)):
        currentSum = 0
        # resets the currentSum
        for j in range(i, len(array)):
            currentSum += array[j]
            # each case, it checks if the currentSUm is greater than the maxSum
            # if so, then updates maxSum
            if currentSum > maxSum:
                maxSum = currentSum
    return maxSum

test = [-2,1,-3,4,-1,2,1,-5,4]
print(maxSubArray(test))