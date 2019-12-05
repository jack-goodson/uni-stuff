# ASSIGNMENT DESCRIPTION

#This second assignment lets you get familiar with divide-and-conquer design and development. We would like you to 
#implement an efficient divide-and-conquer algorithm to solve the Closest Pair prob- lem in plane as elaborated during the lecture. An O(n log n) solution is preferred since we have set the running time limit on the automated marker.
There are 3 test cases whose sizes increase by 10 times. The first one contains 10,000 2D points, while the last largest one comprises of 1000,000 2D points. It is worth 5% of your total course marks. The first and second test cases have 2 marks each and the last has 1 mark.
There will be a penalty if you exceed the submission limit. Therefore, please write a bruteforce algorithm and test your divide-and-conquer version with your own generated inputs before submitting to the automated marker.



def closest_pair(x,y):
    if len(x) < 4:
        distance = (x[0][0] - x[1][0]) ** 2 + (x[0][1] - x[1][1]) ** 2
        if len(x) == 2:
            to_return = [x[0], x[1], distance]
            return to_return
        for n in range(len(x) - 1):
            for m in range(n+1, len(x)):
                if m != 1 and n != 0:
                    d = (x[n][0] - x[m][0]) ** 2 + (x[n][1] - x[m][1]) ** 2
                    if d < distance:
                        distance = d
                        x[0] = x[n]
                        x[1] = x[m]
        to_return = [x[0],x[1], distance]
        return to_return


    first_half_x = x[:len(x)//2]
    second_half_x = x[len(x)//2:]

    l_1, l_2 = [], []
    for y_val in y:
        if y_val[0] <= x[(len(x) // 2)][0]:
            l_1.append(y_val)
        else:
            l_2.append(y_val)

    recur_list_1,recur_list_2 = closest_pair(first_half_x, l_1), closest_pair(second_half_x, l_2)

    if recur_list_1[2] <= recur_list_2[2]:
        delta = recur_list_1[2]
        smallest = [recur_list_1[0], recur_list_1[1]]
    else:
        delta = recur_list_2[2]
        smallest = [recur_list_2[0], recur_list_2[1]]

    mid_x = x[len(x)//2][0]

    delta_x_far = []
    for j in y:
        if mid_x - delta <= j[0] <= mid_x + delta:
            delta_x_far.append(j)

    for x in range(len(delta_x_far) - 1):
        for k in range(x+1, min(x+7, len(delta_x_far))):
            if (delta_x_far[x][0] - delta_x_far[k][0]) ** 2 + (delta_x_far[x][1] - delta_x_far[k][1]) ** 2 < delta:
                smallest = [delta_x_far[x], delta_x_far[k]]
                delta = (delta_x_far[x][0] - delta_x_far[k][0]) ** 2 + (delta_x_far[x][1] - delta_x_far[k][1]) ** 2
    to_return = [smallest[0], smallest[1], delta]
    return to_return

input_size = 1000000
list_of_inputs = []
for i in range(input_size):
    inp = input()
    points = inp.split()
    point_tuple = (int(points[0]), int(points[1]))
    list_of_inputs.append(point_tuple)

sort_by_x = sorted(list_of_inputs, key=lambda x: x[0])
sort_by_y = sorted(list_of_inputs, key=lambda y: y[1])

print(closest_pair(sort_by_x, sort_by_y)[2])

