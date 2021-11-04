//https://habr.com/ru/post/440436/
//        Практические задачи по Java
//        7.5. Ещё более сложный итератор
//
//        Мне нравится эта задача. До неё доходит всего несколько студентов в группе, которые сравнительно легко справляются с предыдущими задачи.
//
//        Задача:
//
//        Дан итератор. Метод next() возвращает либо String, либо итератор такой же структуры
//        (то есть который опять возвращает или String, или такой же итератор).
//        Напишите поверх этого итератора другой, уже «плоский».

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class Iterator2 implements Iterator<String> {

    private Stack<Iterator> iterators;
    private String next;
    private boolean hasNext;

   public Iterator2(Iterator<?> iterator) {
        this.iterators = new Stack<Iterator>();
        iterators.push(iterator);

        updateNext();
    }

   @Override
   public boolean hasNext() {
       return hasNext;
   }

   private void updateNext() {
       if(iterators.empty()) {
           next = null;
           hasNext = false;
           return;
       }

       Iterator current = iterators.peek();

       if (current.hasNext()) {
            Object o = current.next();
            if(o instanceof String) {
                next = (String) o;
                hasNext = true;
            }else if(o instanceof Iterator) {
                Iterator iterator = (Iterator) o;
                iterators.push(iterator);
                updateNext();
            }else{
                throw new IllegalArgumentException();
            }
       }else {
           iterators.pop();
           updateNext();
       }
   }

    @Override
    public String next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        String nextToReturn = next;
        updateNext();
        return nextToReturn;
    }
}
